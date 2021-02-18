package com.example.algamoney.api.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.ExceptionPessoaInativaOuInexistente;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lr;
	
	@Autowired
	private PessoaRepository pr;
	
	public Lancamento validacao(Lancamento lancamento) {
		Pessoa pessoa  = pr.findById(lancamento.getPessoa().getId()).orElse(null);
		
		
		if(lancamento == null || !pessoa.isAtivo()) {
			throw new ExceptionPessoaInativaOuInexistente();
		}else {
			return lr.save(lancamento);
		}
		
		
	}
	
	public Page<Lancamento> listar(org.springframework.data.domain.Pageable pageable){
		return lr.findAll(pageable);
	}
	
}
