package ru.library.library.repository;

import org.hibernate.Session;
import ru.library.library.entity.SoftDelete;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public class SoftDeleteRepositoryImpl<T extends SoftDelete> implements SoftDeleteRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public void softDelete(T obj) {
        Session session = entityManager.unwrap(Session.class);
        obj.setDeleted();
        session.update(obj);
    }

    @Override
    @Transactional
    public void bulkSoftDelete(List<T> list) {
        Session session = entityManager.unwrap(Session.class);
        list.forEach(entry -> {
            entry.setDeleted();
            session.update(entry);
        });

    }
}
