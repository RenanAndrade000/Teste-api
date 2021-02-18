package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("pessoa")
public class PessoaResource {
	
	@Autowired
	PessoaRepository pr;
	
	@Autowired
	ApplicationEventPublisher publisher;
	
	@Autowired
	PessoaService ps;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> inserir(@RequestBody Pessoa pessoa,HttpServletResponse response) {
		if(pessoa.getNome() == null) {
			return ResponseEntity.badRequest().build();
		}else {
			pr.save(pessoa);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getId()));
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		}
		
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		pr.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> update (@PathVariable Long id,@RequestBody Pessoa pessoa) {
		return ps.atualizar(id, pessoa);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateativo(@PathVariable Long id,@RequestBody boolean ativo) {
		ps.atualizarativo(id,ativo);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar (@PathVariable Long id) {
		Pessoa pessoasalva = pr.findById(id).orElse(null);
		if(pessoasalva == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();//sem o build não vai, tem que ter os três parâmetros
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(pr.findById(id));
		}
		
	}
	@GetMapping
	public ResponseEntity<List<Pessoa>> listar(){
			
		if(pr.findAll() != null) {
			return ResponseEntity.ok(pr.findAll());
		}else {
			return ResponseEntity.noContent().build();
		}
			
		
	}
	
	
	
}
