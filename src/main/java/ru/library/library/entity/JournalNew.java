package ru.library.library.entity;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "journal_new")
@Getter
@Setter
public class JournalNew {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "dt")
    private LocalDateTime dateColumn;

    @Column(name = "level")
    private int level;

    @Column(name = "msg")
    private String message;
}
