package br.com.fstmkt.repository;

import br.com.fstmkt.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByCpf(String cpf);

    Optional<Usuario> findUsuarioByTokenLogin(String token);

    Boolean existsByCpf(String cpf);

    Boolean existsByEmail(String email);
}
