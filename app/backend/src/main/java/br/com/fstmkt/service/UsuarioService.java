package br.com.fstmkt.service;

import br.com.fstmkt.dto.UsuarioDTO;
import br.com.fstmkt.dto.shallow.UsuarioShallowDTO;
import br.com.fstmkt.entity.Usuario;
import br.com.fstmkt.mapper.BaseMapper;
import br.com.fstmkt.mapper.UsuarioMapper;
import br.com.fstmkt.repository.BaseRepository;
import br.com.fstmkt.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService extends BaseService<Long, Usuario, UsuarioDTO, UsuarioShallowDTO>
        implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final EntityManager entityManager;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, EntityManager entityManager) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.entityManager = entityManager;
    }

    public List<UsuarioShallowDTO> listarSemPaginacao() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UsuarioShallowDTO> query = criteriaBuilder.createQuery(UsuarioShallowDTO.class);
        Root<Usuario> root = query.from(Usuario.class);
        query.select(criteriaBuilder.construct(UsuarioShallowDTO.class, root.get("id"), root.get("cpf"), root.get("nome"),
                root.get("email"), root.get("dataNascimento"), root.get("roles")));
        return entityManager.createQuery(query).getResultList();
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

    public Usuario buscarUsuarioPorToken(String token) {
        Usuario usuario = usuarioRepository.findUsuarioByTokenLogin(token)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Token: %s, não foi encontrado", token)));
        return usuario;
    }

    public String criptografarSenha(String senha) {
        return bCryptPasswordEncoder.encode(senha);
    }

    @Override
    public Page<UsuarioShallowDTO> listar(UsuarioDTO filter, Pageable pageable) {
        Specification<Usuario> spec = new Specification<Usuario>() {
            @Override
            public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (Objects.nonNull(filter.getCpf())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("cpf")), "%" + filter.getCpf()));
                }

                if (Objects.nonNull(filter.getNome())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + filter.getNome()));
                }

                if (Objects.nonNull(filter.getEmail())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filter.getEmail()));
                }

                if (Objects.nonNull(filter.getDataNascimento())) {
                    predicates.add(criteriaBuilder.equal(root.<LocalDate>get("dataNascimento"), filter.getDataNascimento()));
                }

                if (Objects.nonNull(filter.getRoles())) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roles")), "%" + filter.getRoles()));
                }

                if (Objects.nonNull(filter.getId())) {
                    predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
                }

                return addTogether(predicates, criteriaBuilder);
            }
            private Predicate addTogether(List<Predicate> predicates, CriteriaBuilder cb) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        return getRepository().findAll(spec, pageable).map(getEntityMapper()::EntityToShallowDTO);
    }

    @Override
    protected BaseRepository<Usuario, Long> getRepository() {
        return usuarioRepository;
    }

    @Override
    protected BaseMapper<Usuario, UsuarioDTO, UsuarioShallowDTO> getEntityMapper() {
        return usuarioMapper;
    }
}
