package com.algamoney.model;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lancamento.class)
public abstract class Lancamento_ {

	public static volatile SingularAttribute<Lancamento, Long> codigo;
	public static volatile SingularAttribute<Lancamento, String> observacao;
	public static volatile SingularAttribute<Lancamento, Tipo> tipo;
	public static volatile SingularAttribute<Lancamento, Date> dataPagamento;
	public static volatile SingularAttribute<Lancamento, Pessoa> pessoa;
	public static volatile SingularAttribute<Lancamento, Date> dataVencimento;
	public static volatile SingularAttribute<Lancamento, Categoria> categoria;
	public static volatile SingularAttribute<Lancamento, Double> valor;
	public static volatile SingularAttribute<Lancamento, String> descricao;

	public static final String CODIGO = "codigo";
	public static final String OBSERVACAO = "observacao";
	public static final String TIPO = "tipo";
	public static final String DATA_PAGAMENTO = "dataPagamento";
	public static final String PESSOA = "pessoa";
	public static final String DATA_VENCIMENTO = "dataVencimento";
	public static final String CATEGORIA = "categoria";
	public static final String VALOR = "valor";
	public static final String DESCRICAO = "descricao";

}

