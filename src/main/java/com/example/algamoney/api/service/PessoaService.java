package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pr;
	
	
	public ResponseEntity<Pessoa> atualizar(Long id,Pessoa pessoa) {
		Pessoa psalva = pr.findById(id).orElse(null);//orElseNull útil pra caralho quando não quiser criar Optional
		if(psalva != null) {
			BeanUtils.copyProperties(pessoa, psalva, "id");
			pr.save(psalva);
			return ResponseEntity.ok(psalva);
		}else {
			throw new EmptyResultDataAccessException(1);
		}
		
	}

	
	public void atualizarativo(Long id,boolean ativo) {
		//no vídeo o cara teve uma sacada genial, fez a pessoa receber o metodo de busca
		//ex o metodo de get comum pra trazer um registro. esse metodo estava todo incrementado com trhows e validações etc
		if(pr.findById(id).equals("")) {
			throw new EmptyResultDataAccessException(1);
		}else{
			Pessoa pessoa  = pr.findById(id).orElse(null);
			pessoa.setAtivo(ativo);
			pr.save(pessoa);	
		}
	}
	
}
