package br.com.fstmkt.controller;

import br.com.fstmkt.dto.UsuarioDTO;
import br.com.fstmkt.dto.shallow.UsuarioShallowDTO;
import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.service.BaseService;
import br.com.fstmkt.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController extends BaseController<Long, Usuario, UsuarioDTO, UsuarioShallowDTO> {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @Override
    public BaseService<Long, Usuario, UsuarioDTO, UsuarioShallowDTO> getService() {
        return usuarioService;
    }
}
