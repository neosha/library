package ru.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.library.library.entity.Journal;
import ru.library.library.job.JournalJobLauncher;
import ru.library.library.repository.JournalRepository;

import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private JournalJobLauncher jobLauncher;
    private JournalRepository repository;

    @Autowired
    public JobController(JournalJobLauncher jobLauncher, JournalRepository repository) {
        this.jobLauncher = jobLauncher;
        this.repository = repository;
    }

    @PostMapping("/copyJournal")
    public void startJournalJob(){
        jobLauncher.copyJournal();
    }



    @GetMapping("/getRecord")
    public Optional<Journal> getRecord(@RequestParam(name = "id") Long id){
        return repository.findById(id);
    }
}
