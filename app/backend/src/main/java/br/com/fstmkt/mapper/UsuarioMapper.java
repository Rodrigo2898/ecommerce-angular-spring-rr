package br.com.fstmkt.mapper;

import br.com.fstmkt.dto.UsuarioDTO;
import br.com.fstmkt.dto.shallow.UsuarioShallowDTO;
import br.com.fstmkt.entity.Usuario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO, UsuarioShallowDTO> {
}
