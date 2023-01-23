package ru.library.library.config;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioning implements Partitioner {

    private JdbcOperations jdbcTemplate;
    private String table;
    private String column;

    public void setTable(String table) {
        this.table = table;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = jdbcTemplate.queryForObject("select min(" + column + ") from " + table, Integer.class);
        int max = jdbcTemplate.queryForObject("select max(" + column + ") from " + table, Integer.class);
        int targetSize = (max - min) / gridSize + 1;

        Map<String,ExecutionContext> result = new HashMap<>();
        int leftPtr = min;
        int number = 0;
        int rightPtr = leftPtr + targetSize - 1;

        while (leftPtr <= max) {
            ExecutionContext value = new ExecutionContext();
            if (rightPtr > max) {
                rightPtr = max;
            }
            value.put("leftBound", leftPtr);
            value.put("rightBound", rightPtr);
            result.put("partition" + number, value);
            leftPtr += targetSize;
            rightPtr += targetSize;
            number++;
        }
        return result;
    }
}
