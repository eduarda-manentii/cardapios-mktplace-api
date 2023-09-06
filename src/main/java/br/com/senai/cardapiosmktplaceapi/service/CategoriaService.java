package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TiposDeCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface CategoriaService {

	public Categoria salvar(
			@NotNull(message = "A categoria é obrigatória.") 
			Categoria categoria);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id, 
			@NotNull(message = "O status é obrigatório.")
			Status status);
	
	public Page<Categoria> listarPor(
			@NotBlank(message = "O nome é obrigatório.") 
			@Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres.")
			String nome, 
			@NotNull(message = "O status é obrigatório.") 
			Status status, 
			@NotNull(message = "O tipo da categoria é obrigatório.") 
			TiposDeCategoria tipoDaCategoria, 
			Pageable paginacao);
	
	public Categoria buscarPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id);
	
	public Categoria excluirPor(
			@NotNull(message = "O id para a exclusão é obrigatório.") 
			@Positive(message = "O id para a exclusão deve ser positivo.")
			Integer id);
}
