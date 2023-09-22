package br.com.senai.cardapiosmktplaceapi.dto;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovaOpcaoCardapio {
	
	@NotNull(message = "O id da opção é obirgatório.")
	@Positive(message = "o id da opção deve ser positivo.")
	private Integer idDaOpcao;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser positivo.")
	@Digits(integer = 9, fraction = 2, message = "O preço deve possuir o formato NNNNNNNNN.NN")
	private BigDecimal preco;
	
	@NotNull(message = "O indicador de recomendação é obrigatório.")
	private Confirmacao recomendado;
	
	@NotNull(message = "A seção é obrigatória.")
	private Secao secao;

}
