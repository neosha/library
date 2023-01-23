package ru.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.library.library.entity.MockEntity;

import java.util.UUID;

@Repository
public interface MaintainDataBaseRepository extends JpaRepository<MockEntity, UUID> {
//    @Procedure(value = "public.fill_author_description")
    @Query(value = "call fill_author_description();", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    @Transactional
    public void fillAuthorDescription();
}
