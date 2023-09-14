package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;

@Service
public class CardapioServiceImp implements CardapioService {
	
	@Autowired
	private CardapiosRepository repository;
	
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	@Autowired
	private SecoesRepository secoesRepository;
	
	@Autowired
	private RestaurantesRepository restaurantesRepository;

	@Override
	public Cardapio inserir(NovoCardapio novoCardapio) {
		Restaurante restaurante = getRestaurantePor(novoCardapio);
		Cardapio cardapio = new Cardapio();
		cardapio.setNome(novoCardapio.getNome());
		cardapio.setDescricao(novoCardapio.getDescricao());
		cardapio.setRestaurante(restaurante);
		Cardapio cardapioSalvo = repository.save(cardapio);
		this.validarDuplicidadeEm(novoCardapio.getOpcoes());
		for (NovaOpcaoCardapio novaOpcao : novoCardapio.getOpcoes()) {
			Opcao opcao = getOpcaoPor(novaOpcao.getIdDaOpcao(), restaurante);
			Secao secao = getSecaoPor(novaOpcao);
			OpcaoDoCardapioId id = new OpcaoDoCardapioId(cardapioSalvo.getId(), opcao.getId());
			OpcaoDoCardapio opcaoDoCardapio = new OpcaoDoCardapio();
			opcaoDoCardapio.setId(id);
			opcaoDoCardapio.setCardapio(cardapioSalvo);
			opcaoDoCardapio.setOpcao(opcao);
			opcaoDoCardapio.setSecao(secao);
			opcaoDoCardapio.setPreco(novaOpcao.getPreco());
			opcaoDoCardapio.setRecomendado(novaOpcao.getConfirmacao());
			cardapioSalvo.getOpcoes().add(opcaoDoCardapio);
			this.repository.saveAndFlush(cardapioSalvo);
		}
		return repository.buscarPor(cardapioSalvo.getId());
	}

	@Override
	public Cardapio alterar(CardapioSalvo cardapioSalvo) {
		Restaurante restaurante = restaurantesRepository.buscarPor(cardapioSalvo.getRestaurante().getId());
		Cardapio cardapio = repository.buscarPor(cardapioSalvo.getId());
		cardapio.setNome(cardapioSalvo.getNome());
		cardapio.setDescricao(cardapio.getDescricao());
		cardapio.setRestaurante(restaurante);
		cardapio.setStatus(cardapioSalvo.getStatus());
		Cardapio cardapioAtualizado = repository.saveAndFlush(cardapio);
		return buscarPor(cardapioAtualizado.getId());
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable paginacao) {
		Page<Cardapio> cardapios = repository.listarPor(restaurante, paginacao);
		for (Cardapio cardapio : cardapios.getContent()) {
			this.atualizarPrecosDas(cardapio.getOpcoes());
		}
		return cardapios;
	}

	@Override
	public Cardapio buscarPor(Integer id) {
		Cardapio cardapioEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado, "Não foi encontrado cardápio para o ID informado");
		Preconditions.checkArgument(cardapioEncontrado.isAtiva(), "O cardápio está inativo.");
		this.atualizarPrecosDas(cardapioEncontrado.getOpcoes());
		return cardapioEncontrado;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Cardapio cardapio = buscarPor(id);
		Preconditions.checkArgument(cardapio.getStatus() == status, "O status já foi salvo anteriormente.");
		this.repository.atualizarPor(id, status);
	}
	
	
	//locals methods
	
	private void atualizarPrecosDas(List<OpcaoDoCardapio> opcoesDoCardapio) {
		for (OpcaoDoCardapio opcaoDoCardapio : opcoesDoCardapio) {
			if(opcaoDoCardapio.getOpcao().isEmPromocao()) {
				BigDecimal divisor = new BigDecimal(100);
				BigDecimal percentualDeDesconto = opcaoDoCardapio.getOpcao().getPercentalDeDesconto();
				
				BigDecimal valorDesconto = opcaoDoCardapio.getPreco()
						.multiply(percentualDeDesconto).divide(divisor);
				
				BigDecimal preco = opcaoDoCardapio.getPreco().subtract(valorDesconto).setScale(2, RoundingMode.CEILING);
				
				opcaoDoCardapio.setPreco(preco);
			}
		}
	}
	
	private Restaurante getRestaurantePor(NovoCardapio novoCardapio) {
		Preconditions.checkNotNull(novoCardapio.getRestaurante(), "O restaurante é obrigatório.");
		Restaurante restaurante = restaurantesRepository.buscarPor(novoCardapio.getRestaurante().getId());
		Preconditions.checkNotNull(restaurante, "O restaurante '" + novoCardapio.getRestaurante().getId() + "' não foi encontrado.");
		Preconditions.checkArgument(restaurante.isAtiva(), "O restaurante está inativo.");
		return restaurante;
	}
	
	private Secao getSecaoPor(NovaOpcaoCardapio novaOpcao) {
		Preconditions.checkNotNull(novaOpcao.getSecao(), "A seção da opção é obrigatória.");
		Secao secao = secoesRepository.buscarPor(novaOpcao.getSecao().getId());
		Preconditions.checkNotNull(secao, "A seção '" + novaOpcao.getSecao().getId() + "' não foi encontrada.");
		Preconditions.checkArgument(secao.isAtiva(), "A seção está inativa.");
		return secao;
	}
	
	private Opcao getOpcaoPor(Integer idDaOpcao, Restaurante restaurante) {
		Opcao opcao = opcoesRepository.buscarPor(idDaOpcao);
		Preconditions.checkNotNull(opcao, "A opção '" + idDaOpcao + "' não foi encontrada.");
		Preconditions.checkArgument(opcao.isAtiva(), "A opção não está ativa.");
		Preconditions.checkArgument(opcao.getRestaurante().equals(restaurante), "A opção '" + idDaOpcao + "' não pertence ao cárdapio do restaurante.");
		return opcao;
	}
	
	private void validarDuplicidadeEm(List<NovaOpcaoCardapio> opcoesDoCardapio) {
		for (NovaOpcaoCardapio novaOpcao : opcoesDoCardapio) {
			int qtdeDeOcorrencias = 0;
			for (NovaOpcaoCardapio outraOpcao : opcoesDoCardapio) {
				if (novaOpcao.getIdDaOpcao().equals(outraOpcao.getIdDaOpcao())) {
					qtdeDeOcorrencias++;
				}
			}
			Preconditions.checkArgument(qtdeDeOcorrencias == 1, "A opção '" + novaOpcao.getIdDaOpcao() + "' está duplicado no cardápio.");
		}
	}

}
