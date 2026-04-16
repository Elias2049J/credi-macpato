package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.mapper.RestMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrudService<RES, REQ, ID, T> {

    JpaRepository<T, ID> repository();
    RestMapper<T, RES, REQ> mapper();

    default T getEntityById(ID id) {
        return repository().findById(id).orElseThrow(() -> new IllegalArgumentException("Resource not found"));
    }

    default List<RES> findAll() {
        return repository().findAll().stream().map(mapper()::toResponse).toList();
    }

    default RES create(REQ request) {
        T entity = mapper().toEntity(request);
        return mapper().toResponse(repository().save(entity));
    }

    RES update(ID id, REQ request);

    default RES findById(ID id) {
        return mapper().toResponse(getEntityById(id));
    }

    default void delete(ID id) {
        repository().deleteById(id);
    }
}
