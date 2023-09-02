package br.com.senai.cardapiosmktplaceapi.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Embeddable
@AllArgsConstructor @Data
public class OpcaoDoCardapioId {

		@Column(name = "id_cardapio")
		private Integer IdDocardapio;
		
		@Column(name = "id_opcao")
		private Integer idDaOpcap;
}
