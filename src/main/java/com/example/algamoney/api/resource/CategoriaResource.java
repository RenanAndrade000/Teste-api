package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository cr;
	
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		
		List<Categoria> categorias = cr.findAll(); //criando lista de categorias e jogar o findAll do repository nela
		List<Categoria> categoria = cr.findAll();
		if(categoria == null) { //caso o resultado seja nulo, ou seja, caso não tenha nenhum registro no banco
			return ResponseEntity.noContent().build(); //notFound retorna o status 404 ao invés do OK 200. o build() é necessário pra gerar a resposta 
		}else {
			return ResponseEntity.ok(categorias); //retorna ok 200
		}	
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)//retorna o status 201 created
	public ResponseEntity<Categoria> inserir (@RequestBody Categoria categoria,HttpServletResponse response) {
		categoria = cr.save(categoria);
		
		//Resumo: serve para dar uma resposta através do header, onde uri foi salva ex localhost://categorias/10 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		//ServletURIComponentsBuilder serve para pegar a uri da requisição (FromCurrentRequestUri)path serve para pegar o id da categoria
		//(categoria.getID) e transformar em uri response(objeto tipo HttpServletResponse criada no parâmetro do metodo) irá setar o
		//header location(não sei o motivo, mas parece padrão),uri para string
		
		return ResponseEntity.created(uri).body(categoria); //retorna o status created da uri no parâmetro. body(categoria)
		//retorna no body a entidade categoria que foi inserida
		//detalhe não muito importante: a anotação @ResponseStatus lá em cima não é mais necessária por culpa do .created(uri)
	}
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscar(@PathVariable Long id) {
		Optional<Categoria> categoria  = cr.findById(id);
		if(categoria == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(categoria);
		}
	}
	
}
