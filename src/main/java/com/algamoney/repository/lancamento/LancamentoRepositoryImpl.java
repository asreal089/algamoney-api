package com.algamoney.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LacamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> critiria = cb.createQuery(Lancamento.class);
		Root<Lancamento> root = critiria.from(Lancamento.class);
		
		Predicate[] predicates = criarFiltro(lancamentoFilter, cb, root);
		
		critiria.where(predicates);
		TypedQuery<Lancamento> query = entityManager.createQuery(critiria);
		
		adicionarRestricoesDePagina(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter)); 
	}

	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		Predicate[] predicados = criarFiltro(lancamentoFilter, cb, root);
		criteria.where(predicados);
		criteria.select(cb.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePagina(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalDeRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual* totalDeRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalDeRegistrosPorPagina);
		
	}

	private Predicate[] criarFiltro(LancamentoFilter lancamentoFilter, CriteriaBuilder cb, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(lancamentoFilter.getDescricao()!= null) {
			predicates.add(cb.like(cb.lower(root.get("descricao")), "%"+lancamentoFilter.getDescricao().toLowerCase()+"%"));
		}
		if(lancamentoFilter.getDataVencimentoAte()!=null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte().toString()));
		}
		if(lancamentoFilter.getDataVencimentoDe()!=null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
