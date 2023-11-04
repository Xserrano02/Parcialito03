package com.mitocode.repo;

import com.mitocode.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID> {
}
