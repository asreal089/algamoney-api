package com.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoney.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
}
