package com.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.lancamento.LacamentoRepositoryQuery;


public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LacamentoRepositoryQuery {
	
}
