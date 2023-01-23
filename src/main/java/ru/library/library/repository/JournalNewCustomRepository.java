package ru.library.library.repository;

import ru.library.library.entity.JournalNew;

import java.util.List;


public interface JournalNewCustomRepository {
    void batchInsertJournalNew(List<JournalNew> list);

    List<JournalNew> getFirst100RecordsOrderById();

}
