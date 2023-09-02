package br.com.senai.cardapiosmktplaceapi.entity;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "cardapios")
@Entity(name = "Cardapio")
public class Cardapio {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "O nome do cardápio é obrigatório.")
	@Size(max = 100, message = "O nome da categoria não deve conter mais de 100 caracteres.")
	@Column(name = "nome")
	private String nome;
	
	@NotBlank(message = "A descrição do cardápio é obrigatória.")
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull(message = "O status do cardápio é obrigatório.")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_restaurante")
	@NotNull(message = "O restaurante é obrigatório.")
	private Restaurante restaurante;
	
	@Transient
	public boolean isPersistida() {
		return getId() != null && getId() > 0;
	}
	
	@Transient
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}

}
