package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CarregarOpcao {
	
	@NotBlank(message = "O nome é obrigatório.")
	@Size(min = 3, message = "O nome para listagem é até 3 caracteres.")
	private String nome;
	
	@NotNull(message = "O restaurante é obrigatório.")
    private Integer idDoRestaurante;
	
	@NotNull(message = "A cateogira é obrigatória.")
    private Integer idDaCategoria;

}
