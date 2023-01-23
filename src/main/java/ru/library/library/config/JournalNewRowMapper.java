package ru.library.library.config;

import org.springframework.jdbc.core.RowMapper;
import ru.library.library.entity.JournalNew;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class JournalNewRowMapper implements RowMapper<JournalNew> {
    @Override
    public JournalNew mapRow(ResultSet rs, int rowNum) throws SQLException {
        JournalNew journalNew = new JournalNew();
        journalNew.setId(rs.getLong("id"));
        journalNew.setLevel(rs.getInt("level"));
        journalNew.setMessage(rs.getString("msg"));
        journalNew.setDateColumn(rs.getObject("dt", LocalDateTime.class));
        return journalNew;
    }
}
