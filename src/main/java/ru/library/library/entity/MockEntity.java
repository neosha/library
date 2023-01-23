package ru.library.library.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class MockEntity {
    @Id
    private UUID id;
}
