package com.algamoney.exceptionHandler;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgaMoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		String menssagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensageDesenvolvedor = ex.getCause().toString();
		List<Erro> erros = Arrays.asList(new Erro(menssagemUsuario, mensageDesenvolvedor));
		return  handleExceptionInternal(ex , erros ,headers, HttpStatus.BAD_REQUEST, request); 
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		
		return  handleExceptionInternal(ex , erros ,headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAcessException(EmptyResultDataAccessException ex, WebRequest request) {
		String menssagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensageDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(menssagemUsuario, mensageDesenvolvedor));
		return  handleExceptionInternal(ex , erros ,new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		
		for(FieldError fieldError: bindingResult.getFieldErrors()) {
			
			String menssagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String menssagemDesenvolvedor = fieldError.toString();
			
			erros.add(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		}
		
		return erros;
	}

	public static class Erro{
		private String menssagemUsuario;
		private String menssagemDesenvolvedor;
		
		public Erro(String menssagemUsuario,String menssagemDesenvolvedor) {
			this.menssagemDesenvolvedor = menssagemDesenvolvedor;
			this.menssagemUsuario = menssagemUsuario;
		}
		
		public String getMenssagemUsuario() {
			return menssagemUsuario;
		}

		public String getMenssagemDesenvolvedor() {
			return menssagemDesenvolvedor;
		}
	}

}
