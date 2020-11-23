package com.algamoney.exceptionHandler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
		return  handleExceptionInternal(ex , new Erro(menssagemUsuario, mensageDesenvolvedor) ,headers, HttpStatus.BAD_REQUEST, request); 
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
