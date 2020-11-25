package com.algamoney.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.event.RecursoCriadoEvent;
import com.algamoney.model.Lancamento;
import com.algamoney.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
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
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
}
