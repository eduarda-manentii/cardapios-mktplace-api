package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface CardapioService  {
	
	public Categoria inserir(
			@NotNull(message = "A categoria é obrigatória.") 
			Categoria categoria);
	
	public void alterar(
			@Valid
			@NotNull(message = "O cardápio salvo é obrigatório.")
			CardapioSalvo cardapioSavlo);
	
	public Page<Categoria> listarPor(
			@NotNull(message = "O restaurante é obrigatório.")
			Restaurante restaurante,
			Pageable paginacao);
	
	public Categoria buscarPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id, 
			@NotNull(message = "O status é obrigatório.")
			Status status);
	
	public Categoria excluirPor(
			@NotNull(message = "O id para a exclusão é obrigatório.") 
			@Positive(message = "O id para a exclusão deve ser positivo.")
			Integer id);

}
