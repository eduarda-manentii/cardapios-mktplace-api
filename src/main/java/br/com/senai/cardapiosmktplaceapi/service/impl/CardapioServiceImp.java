package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;

@Service
public class CardapioServiceImp implements CardapioService {

	@Override
	public Cardapio inserir(NovoCardapio novoCardapio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cardapio alterar(CardapioSalvo cardapioSalvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable paginacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cardapio buscarPor(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		// TODO Auto-generated method stub
	}


}
