package ru.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.library.entity.Author;

import java.util.List;
import java.util.UUID;
@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID>, SoftDeleteRepository<Author> {

    List<Author> findByName(String name);
}
