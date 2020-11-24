package com.algamoney.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.event.RecursoCriadoEvent;
import com.algamoney.model.Pessoa;
import com.algamoney.repository.PessoaRepository;
import com.algamoney.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<?> listaPessoas(){
		List<?> pessoas = pessoaRepository.findAll();
		return !(pessoas.isEmpty())? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> getPessoa(@PathVariable Long codigo){
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		return !(pessoa.isEmpty())?ResponseEntity.ok(pessoa): ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criarPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPessoa(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<?>  atualizaPessoa(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Optional<Pessoa> pessoaSalva = pessoaService.atualizarPessoa(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaPropriedadeAtivo(@PathVariable Long codigo,@RequestBody Boolean ativo) {
		pessoaService.atualizaPropriedadeAtivo(codigo, ativo);
	}
	
	
}
