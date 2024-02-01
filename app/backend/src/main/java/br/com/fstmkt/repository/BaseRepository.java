package br.com.fstmkt.repository;

import br.com.fstmkt.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<ID>, ID> extends CrudRepository<T, ID>,
        PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> {
}
