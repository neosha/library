package ru.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.library.entity.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal,Long> {
}
