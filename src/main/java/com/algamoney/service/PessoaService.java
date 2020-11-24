package com.algamoney.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoney.model.Pessoa;
import com.algamoney.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	public Optional<Pessoa> atualizarPessoa(Long codigo, Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = getPessoaByID(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaRepository.save(pessoaSalva.get());
		
		return pessoaSalva;
	}
	
	public void atualizaPropriedadeAtivo(Long codigo, Boolean ativo) {
		Optional<Pessoa> pessoaSalva = getPessoaByID(codigo);
		pessoaSalva.get().setAtivo(ativo);
		pessoaRepository.save(pessoaSalva.get());
	}

	private Optional<Pessoa> getPessoaByID(Long codigo) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
		if(pessoaSalva.isEmpty()) {
			throw new EmptyResultDataAccessException(1) ;
		}
		return pessoaSalva;
	}

}
