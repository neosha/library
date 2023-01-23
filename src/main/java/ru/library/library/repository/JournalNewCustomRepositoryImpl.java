package ru.library.library.repository;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.library.library.entity.JournalNew;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JournalNewCustomRepositoryImpl implements JournalNewCustomRepository {
    private final Logger logger = LoggerFactory.getLogger(JournalNewCustomRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    public void batchInsertJournalNew(List<JournalNew> list) {
        Session hibernateSession = em.unwrap(Session.class);
//        String sql = "insert into journal_new (id, level, dt, msg) VALUES (:id, :level, :dt, :msg)";
        String sql = "insert into journal_new (id, level, dt, msg) VALUES (?, ?, ?, ?)";
        hibernateSession.doWork(connection -> {
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                int counter = 0;
                for(JournalNew journal: list){
                    preparedStatement.setLong(1, journal.getId());
                    preparedStatement.setInt(2, journal.getLevel());
                    preparedStatement.setString(4, journal.getMessage());
                    preparedStatement.setObject(3, journal.getDateColumn());
                    preparedStatement.addBatch();
                    counter++;
                    if (counter % 100 == 0){
                        preparedStatement.executeBatch();
                    }
                }
                preparedStatement.executeBatch();
            }
            catch (SQLException e){
                logger.error("an exception occur in JournalNewCustomRepository.batchInsertJournalNew", e);
            }
        });
    }

    @Override
    public List<JournalNew> getFirst100RecordsOrderById() {
        return em.createNativeQuery("select p from journal p order by id", JournalNew.class).setMaxResults(100).getResultList();
    }
}
