package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OpcaoServiceImpl implements OpcaoService {
	
	@Override
	public Opcao salvar(@NotNull(message = "A opção é obrigatória.") Opcao opcao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Opcao> listarPor(Categoria categoria, Restaurante restaurante, Pageable paginacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria buscarPor(
			@NotNull(message = "O id é obrigatório.") @Positive(message = "O id deve ser positivo.") Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatusPor(
			@NotNull(message = "O id é obrigatório.") @Positive(message = "O id deve ser positivo.") Integer id,
			@NotNull(message = "O status é obrigatório.") Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public Opcao excluirPor(
			@NotNull(message = "O id para a exclusão é obrigatório.") @Positive(message = "O id para a exclusão deve ser positivo.") Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
