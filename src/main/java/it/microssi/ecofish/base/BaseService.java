package it.microssi.ecofish.base;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class BaseService <D, E extends BaseEntity, K> {
    @Autowired protected BaseRepository<E, K> repository;
    @Autowired protected BaseMapper<D, E> mapper;
    @Autowired private EntityManager em;

    public D findById(K id) {
        return mapper.mapToDto(repository.findById(id).orElse(null));
    }

    public List<D> findAll(){
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

    public void deleteById(K id){
        repository.deleteById(id);
    }

}
