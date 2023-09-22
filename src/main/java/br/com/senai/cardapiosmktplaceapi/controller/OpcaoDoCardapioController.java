package br.com.senai.cardapiosmktplaceapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/opcoesDoCardapio")
public class OpcaoDoCardapioController {
	
	@Autowired
	private MapConverter converter;
	
	@Autowired @Qualifier("opcaoDoCardapioServiceProxy")
	private OpcaoDoCardapioService service;
	
	@PostMapping
	public ResponseEntity<?> inserir(
			@RequestBody
			OpcaoDoCardapio opcaoDoCardapio) {
		OpcaoDoCardapio opcaoDoCardapioSalva = service.inserir(opcaoDoCardapio);
		OpcaoDoCardapioId id = opcaoDoCardapioSalva.getId();
		String idDaOpcao = id.getIdDaOpcao().toString();
		String idDoCardapio = id.getIdDocardapio().toString();
		return ResponseEntity.created(URI.create("/opcoesDoCardapio/id/" + idDoCardapio + "/opcao/" + idDaOpcao)).build();
	}
	
	@GetMapping("/cardapio/{cardapio}/opcao/{opcao}")
	public ResponseEntity<?> buscarPor(@PathVariable("cardapio") Cardapio cardapio, 
			@PathVariable("opcao") Opcao opcao) {
	    OpcaoDoCardapio opcaoDoCardapioEncontrada = service.buscarPor(opcao, cardapio);
	    return ResponseEntity.ok(converter.toJsonMap(opcaoDoCardapioEncontrada));
	}
	
	@PutMapping @Transactional
	public ResponseEntity<?> atualizar(@RequestBody OpcaoDoCardapio novaOpcaoDoCardapio) {
		this.service.atualizar(novaOpcaoDoCardapio);
		OpcaoDoCardapio opcaoDoCardapioAlterado = service.buscarPor(novaOpcaoDoCardapio.getOpcao(), novaOpcaoDoCardapio.getCardapio());
		return ResponseEntity.ok(converter.toJsonMap(opcaoDoCardapioAlterado));
	}

}
