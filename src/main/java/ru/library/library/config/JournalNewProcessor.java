package ru.library.library.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.library.library.entity.Journal;
import ru.library.library.entity.JournalNew;

@Component
public class JournalNewProcessor implements ItemProcessor<Journal, JournalNew> {
    @Override
    public JournalNew process(Journal journal) throws Exception {
        JournalNew journalNew = new JournalNew();
        //journalNew.setLevel(journal.getLevel() + 1);
        journalNew.setId(journal.getId());
        journalNew.setDateColumn(journal.getDateColumn());
        journalNew.setMessage(journal.getMessage().toUpperCase());
        return journalNew;
    }
}
