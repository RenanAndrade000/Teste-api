package com.example.algamoney.api.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.algamoney.api.service.exception.ExceptionPessoaInativaOuInexistente;

@ControllerAdvice
public class AlgamoneyExeptionHandler extends ResponseEntityExceptionHandler {
	
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex,"Mensagem inválida", headers,HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({org.springframework.dao.EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(org.springframework.dao.EmptyResultDataAccessException ex,WebRequest request) {
		return handleExceptionInternal(ex,"Recurso não encontrado", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,WebRequest request){
		return handleExceptionInternal(ex,"Operação não permitida",new HttpHeaders(),HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ExceptionPessoaInativaOuInexistente.class})
	public ResponseEntity<Object> handlePessoaInativaOuInexistente(ExceptionPessoaInativaOuInexistente ex,WebRequest request){
		return handleExceptionInternal(ex,"Pessoa Inativa ou Inexistente", new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
	}
	
	
}
