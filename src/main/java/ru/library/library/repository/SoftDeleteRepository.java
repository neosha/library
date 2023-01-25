package ru.library.library.repository;

import ru.library.library.entity.SoftDelete;

import java.util.List;

public interface SoftDeleteRepository<T extends SoftDelete> {
    public void softDelete(T obj);
    public void bulkSoftDelete(List<T> list);
}
