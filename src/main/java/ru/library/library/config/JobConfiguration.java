package ru.library.library.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;
import ru.library.library.entity.Journal;
import ru.library.library.entity.JournalNew;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private DataSource dataSource;
    private JournalNewWriter journalNewWriter;

    private JournalNewProcessor journalNewProcessor;


    @Autowired
    public JobConfiguration(JobBuilderFactory jobBuilderFactory,
                            StepBuilderFactory stepBuilderFactory,
                            DataSource dataSource,
                            JournalNewWriter journalNewWriter,
                            JournalNewProcessor journalNewProcessor) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.journalNewWriter = journalNewWriter;
        this.journalNewProcessor = journalNewProcessor;
    }


    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("ap_batch");
    }

    @Bean ThreadPoolTaskExecutor poolTaskExecutor(){
        int pollSize = 2;
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("ap_batch");
        taskExecutor.setMaxPoolSize(pollSize);
        taskExecutor.setCorePoolSize(pollSize);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        return taskExecutor;
    }

    @Bean
    public ColumnRangePartitioning partitioner(){
        ColumnRangePartitioning columnRangePartitioning = new ColumnRangePartitioning();
        columnRangePartitioning.setColumn("id");
        columnRangePartitioning.setTable("journal");
        columnRangePartitioning.setDataSource(dataSource);
        return columnRangePartitioning;
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Journal> pagingItemReader(@Value("#{stepExecutionContext['leftBound']}") Long leftBound,
                                                          @Value("#{stepExecutionContext['rightBound']}")Long rightBound){
        System.out.println("reading data from " + leftBound + " to " + rightBound);

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);
        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSortKeys(sortKeys);
        queryProvider.setFromClause("from journal");
        queryProvider.setSelectClause("id, dt, level, msg");
        queryProvider.setWhereClause("where id >= " + leftBound + " and id <= " + rightBound);

        JdbcPagingItemReader<Journal> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(1000);
        reader.setRowMapper(new JournalRowMapper());
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Journal> journalItemWriter(){
        JdbcBatchItemWriter<Journal> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into journal_new(id, dt, level, msg) values(:id, :dateColumn, :level, :message)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.afterPropertiesSet();
        return writer;
    }
    @Bean
    public Step slaveStep(){
        return stepBuilderFactory.get("slaveStep")
//                .transactionManager()
                .<Journal, JournalNew>chunk(1000)
                .reader(pagingItemReader(null,null))
//                .writer(journalItemWriter())
                .processor(journalNewProcessor)
                .writer(journalNewWriter)
                .build();
    }

    @Bean
    public Step masterStep(){
        return stepBuilderFactory.get("masterStep")
                .partitioner(slaveStep().getName(), partitioner())
                .step(slaveStep())
                .gridSize(4)
                .taskExecutor(poolTaskExecutor())
                .build();
    }

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler retVal = new TaskExecutorPartitionHandler();
        retVal.setTaskExecutor(poolTaskExecutor());
        retVal.setStep(slaveStep());
        retVal.setGridSize(10);
        return retVal;
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("journalJob")
                .start(masterStep())
                .build();
    }

}
