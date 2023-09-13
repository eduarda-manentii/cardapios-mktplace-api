package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface OpcaoDoCardapioService {
	
	public OpcaoDoCardapio salvar(@NotNull(message = "A opção do cardápio é obrigatória.")  OpcaoDoCardapio opcaoDoCardapio);

	public OpcaoDoCardapio excluirPor(
			@NotNull(message = "O id para a exclusão é obrigatório.") 
			@Positive(message = "O id para a exclusão deve ser positivo.")
			Integer id);
	
	public OpcaoDoCardapio buscarPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id);
}
