package com.example.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		HttpServletResponse response = event.getResponse();
		Long cod = event.getCod();
		
		adicionarheader(response, cod);
		
	}

	private void adicionarheader(HttpServletResponse response, Long cod) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cod).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}
	
}
