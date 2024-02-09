package br.com.fstmkt.repository;

import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.entity.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByCpf(String cpf);

    Boolean existsByCpf(String cpf);

    Boolean existsByEmail(String email);

    @Query("SELECT DISTINCT r FROM Usuario u JOIN u.roles r WHERE u.cpf = :cpfUser")
    Set<Role> findRolesByUsuarioCpf(@Param("cpfUser") String cpfUser);

}
