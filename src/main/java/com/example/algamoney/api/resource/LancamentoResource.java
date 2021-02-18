package com.example.algamoney.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {
	
	@Autowired 
	LancamentoRepository lr;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Autowired
	LancamentoService ls;
	
	@GetMapping
	public ResponseEntity<Page<Lancamento>> listar(org.springframework.data.domain.Pageable pageable){
		return ResponseEntity.ok(ls.listar(pageable));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscar(@PathVariable Long id){
		Lancamento lancamentosalvo = lr.findById(id).orElse(null);
		if(lancamentosalvo == null) {
			return ResponseEntity.noContent().build();
		}else {
			Lancamento lancamento = lr.findById(id).orElse(null);
			return ResponseEntity.ok().body(lancamento);
		}
	}
	
	@PostMapping
	public void inserir(@RequestBody Lancamento lancamento){
		ls.validacao(lancamento);
	
	}
	
	@DeleteMapping
	@RequestMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		Lancamento lancamento = lr.findById(id).orElse(null);
		if(lancamento == null) {
			throw new EmptyResultDataAccessException(1);
		}else {
			lr.deleteById(id);
		}
	}
	
	
}
