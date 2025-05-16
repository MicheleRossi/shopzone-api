package it.microssi.ecofish.base;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<D, E extends BaseEntity> {

    public abstract D mapToDto(E source);

    public abstract E mapToEntity(D source);

    public List<D> mapToDtoList(List<E> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<E> mapToEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
