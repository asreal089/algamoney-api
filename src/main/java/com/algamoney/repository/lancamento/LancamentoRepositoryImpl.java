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

import com.algamoney.model.Lancamento;
import com.algamoney.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LacamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> critiria = cb.createQuery(Lancamento.class);
		Root<Lancamento> root = critiria.from(Lancamento.class);
		
		Predicate[] predicates = criarFiltro(lancamentoFilter, cb, root);
		
		critiria.where(predicates);
		TypedQuery<Lancamento> query = entityManager.createQuery(critiria);
		System.out.println(query.toString());
		return query.getResultList(); 
	}

	private Predicate[] criarFiltro(LancamentoFilter lancamentoFilter, CriteriaBuilder cb, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!lancamentoFilter.getDescricao().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("descricao")), "%"+lancamentoFilter.getDescricao().toLowerCase()+"%"));
		}
		if(lancamentoFilter.getDataVencimentoAte()!=null) {
			predicates.add(cb.lessThanOrEqualTo(root.get("data_vencimento"), lancamentoFilter.getDataVencimentoAte().toString()));
		}
		if(lancamentoFilter.getDataVencimentoDe()!=null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get("data_vencimento"), lancamentoFilter.getDataVencimentoDe()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
}
