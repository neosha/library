package ru.library.library.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.library.library.entity.JournalNew;

import java.time.LocalDateTime;

@Repository
public interface JournalNewRepository extends JpaRepository<JournalNew,Long>, JournalNewCustomRepository {


    @Modifying
    @Query(value = "insert into journal_new (id, level, dt, msg) VALUES (:id, :level, :dt, :msg)", nativeQuery = true)
    void insertJournal(@Param("id") Long id,
                       @Param("level") int level,
                       @Param("dt") LocalDateTime localDateTime,
                       @Param("msg") String msg);

}
