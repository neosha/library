package ru.library.library.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book  extends SoftDelete{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "author_id")
    private UUID author_id;

}
