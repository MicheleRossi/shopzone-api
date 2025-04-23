package it.microssi.ecofish.base;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BaseMapper<D, E extends BaseEntity> {

    private final ModelMapper modelMapper;
    private final Class<D> dtoClass;
    private final Class<E> entityClass;

    public BaseMapper(ModelMapper modelMapper, Class<D> dtoClass, Class<E> entityClass) {
        this.modelMapper = modelMapper;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public D mapToDto(E source) {
        return modelMapper.map(source, dtoClass);
    }

    public E mapToEntity(D source) {
        return modelMapper.map(source, entityClass);
    }

    public List<D> mapToDtoList(List<E> entityList) {
        return entityList.stream()
                .map(source -> modelMapper.map(source, dtoClass))
                .collect(Collectors.toList());
    }

    public List<E> mapToEntityList(List<D> dtoList) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, entityClass))
                .collect(Collectors.toList());
    }
}
