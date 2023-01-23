package ru.library.library.job;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JournalJobLauncher {

    private JobLauncher jobLauncher;
    private Job job;

    @Autowired
    public JournalJobLauncher(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void copyJournal() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobId", String.valueOf(System.currentTimeMillis()))
                .addDate("date", new Date())
                .addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Status :: " + execution.getStatus());
        }
        catch ( JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException
                | JobParametersInvalidException| JobRestartException e){
            e.printStackTrace();
        }


    }
}
