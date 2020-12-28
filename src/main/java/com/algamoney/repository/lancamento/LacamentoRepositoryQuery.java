package com.algamoney.repository.lancamento;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.filter.LancamentoFilter;

public interface LacamentoRepositoryQuery {
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

	
}
