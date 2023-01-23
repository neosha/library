package ru.library.library.config;


import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.library.library.entity.JournalNew;
import ru.library.library.repository.JournalNewRepository;

import java.util.List;

@Component
public class JournalNewWriter implements ItemWriter<JournalNew> {

    JournalNewRepository journalNewRepo;

    @Autowired
    public JournalNewWriter(JournalNewRepository journalNewRepo) {
        this.journalNewRepo = journalNewRepo;
    }

    @Override
    public void write(List<? extends JournalNew> list) throws Exception {
//        for(JournalNew data: list){
////            journalNewRepo.save(data);
//            journalNewRepo.insertJournal(data.getId(), data.getLevel(), data.getDateColumn(),data.getMessage());
//        }
        journalNewRepo.batchInsertJournalNew((List<JournalNew>) list);
//        journalNewRepo.saveAll(list);
    }
}


