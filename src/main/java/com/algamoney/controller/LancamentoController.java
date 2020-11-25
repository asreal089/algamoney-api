package com.algamoney.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.model.Lancamento;
import com.algamoney.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	@Autowired 
	private LancamentoRepository lancamentoRepository;
	
	@GetMapping
	public ResponseEntity<?> getLancamentos(){
		List<?> lancamentos = lancamentoRepository.findAll();
		
		return !lancamentos.isEmpty()? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> getLancamentos(@PathVariable Long codigo){
		Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
		
		return !lancamento.isEmpty()? ResponseEntity.ok(lancamento) : ResponseEntity.noContent().build();
	}
	
}
