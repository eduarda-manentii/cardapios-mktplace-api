package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface OpcaoService {
	
	public Opcao salvar(@NotNull(message = "A opção é obrigatória.")  Opcao opcao);
	
	public Page<Opcao> listarPor(Categoria categoria, Restaurante restaurante, Pageable paginacao);

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
	
	public Opcao excluirPor(
			@NotNull(message = "O id para a exclusão é obrigatório.") 
			@Positive(message = "O id para a exclusão deve ser positivo.")
			Integer id);

}
