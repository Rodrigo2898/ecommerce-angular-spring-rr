package br.com.fstmkt.mapper;

public interface BaseMapper<Entity, DTO, ShallowDTO> {
    DTO EntityToDTO(Entity entity);

    Entity DTOToEntity(DTO dto);

    ShallowDTO EntityToShallowDTO(Entity entity);
}
