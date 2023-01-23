package ru.library.library.config;

import org.springframework.jdbc.core.RowMapper;
import ru.library.library.entity.Journal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class JournalRowMapper implements RowMapper<Journal> {
    @Override
    public Journal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Journal journal = new Journal();
        journal.setId(rs.getLong("id"));
        journal.setLevel(rs.getInt("level"));
        journal.setMessage(rs.getString("msg"));
        journal.setDateColumn(rs.getObject("dt", LocalDateTime.class));
        return journal;
    }
}
