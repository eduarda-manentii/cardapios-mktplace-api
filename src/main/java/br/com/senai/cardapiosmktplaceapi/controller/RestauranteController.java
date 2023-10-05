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

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("restauranteServiceProxy")
	private RestauranteService service;
	
	@PostMapping
	public ResponseEntity<?> inserir(
			@RequestBody
			Restaurante restaurante) {
		Preconditions.checkArgument(!restaurante.isPersistido(), "O restaurante não pode possuir ID na inserção.");
		Restaurante restauranteSalvo = service.salvar(restaurante);
		return ResponseEntity.created(URI.create("/restaurantes/id/" + restauranteSalvo.getId())).build();
	}
	
	@PutMapping
	public ResponseEntity<?> alterar(
			@RequestBody
			Restaurante restaurante) {
		Preconditions.checkArgument(restaurante.isPersistido(), "O restaurante deve possuir id para a alteração.");
		Restaurante restauranteAtualizado = service.salvar(restaurante);
		return ResponseEntity.ok(converter.toJsonMap(restauranteAtualizado));
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(@PathVariable("id") Integer id) {
		Restaurante restauranteEncontrado = service.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(restauranteEncontrado));
	}
	
}
