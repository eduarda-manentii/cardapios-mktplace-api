package br.com.senai.cardapiosmktplaceapi.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class NovoCardapio {
	
	@NotBlank(message = "O nome é obrigatório.")
	@Size(max = 100, message = "O nome deve conter até 100 caracteres.")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatória.")
	private String descricao;
	
	@NotNull(message = "O restaurante é obrigatório.")
	private Restaurante restaurante;

	@Size(min = 1, message = "O cardápio deve possuir ao menos 1 opção.")
	private List<NovaOpcaoCardapio> opcoes;
	
	public NovoCardapio() {
		this.opcoes = new ArrayList<>();
	}
	
}
