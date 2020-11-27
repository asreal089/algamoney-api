package com.algamoney.repository.lancamento;

import java.util.List;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.filter.LancamentoFilter;

public interface LacamentoRepositoryQuery {
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
