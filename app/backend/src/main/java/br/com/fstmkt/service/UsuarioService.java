package br.com.fstmkt.service;

import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.mapper.UsuarioMapper;
import br.com.fstmkt.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUsuarioByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário: %s, não foi encontrado", cpf)));
        return usuario;
    }

    public Usuario buscarUsuarioPorUsername(String cpf) {
        Usuario usuario = usuarioRepository.findUsuarioByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuário: %s, não foi encontrado", cpf)));
        return usuario;
    }

    public Usuario buscarUsuarioPorTokenLogin(String token) {
        Usuario usuario = usuarioRepository.findUsuarioByTokenLogin(token)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Token: %s, não foi encontrado", token)));
        return usuario;
    }

    public String criptografarSenha(String senha) {
        return bCryptPasswordEncoder.encode(senha);
    }
}
