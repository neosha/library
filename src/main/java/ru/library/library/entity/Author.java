package ru.library.library.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name= "author")
@Getter
@Setter
@Where(clause = "is_deleted in 'N'")
public class Author  extends SoftDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "age")
    private int age;

    public Author() {
    }
}
