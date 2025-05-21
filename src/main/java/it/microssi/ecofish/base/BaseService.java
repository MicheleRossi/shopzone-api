package it.microssi.ecofish.base;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Transactional
public abstract class BaseService <D, E extends BaseEntity, K> {
    @Autowired protected BaseRepository<E, K> repository;
    @Autowired protected BaseMapper<D, E> mapper;
    @Autowired private EntityManager em;

    public D get(K id) {
        return mapper.mapToDto(repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public List<D> get(){
        return mapper.mapToDtoList(repository.findAll());
    }

    public boolean existsById(K id){
        return repository.existsById(id);
    }

    public D save(D dto) {
        var entity = mapper.mapToEntity(dto);
        E saved = repository.saveAndFlush(entity);
        em.refresh(saved);
        return mapper.mapToDto(saved);
    }

    public void delete(K id){
        repository.deleteById(id);
    }

}
