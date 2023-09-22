package br.com.senai.cardapiosmktplaceapi.service.impl;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceImpl implements OpcaoDoCardapioService {
	
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	@Autowired
	private CardapiosRepository cardapiosRepository;
	  
	@Autowired
	private OpcoesDoCardapioRepository repository;

	public OpcaoDoCardapio inserir(OpcaoDoCardapio opcaoDoCardapio) {
		System.out.println("O ID TA VINDO ASSIM: " + opcaoDoCardapio.getId());
		Opcao opcaoEncontrada = getOpcaoPor(opcaoDoCardapio.getOpcao().getId());
		Cardapio cardapioEncontrado = getCardapioPor(opcaoDoCardapio.getCardapio().getId());
		
	    Preconditions.checkNotNull(opcaoDoCardapio.getOpcao().getId(), "O id da opção é obrigatório.");
	    
	    Preconditions.checkNotNull(opcaoDoCardapio.getSecao(), "A seção é obrigatória.");
	    Preconditions.checkArgument(opcaoDoCardapio.getPreco() != null && opcaoDoCardapio.getPreco().compareTo(BigDecimal.ZERO) > 0,
	            "O preço deve ser maior que zero.");
	    Preconditions.checkArgument(opcaoDoCardapio.getRecomendado() == Confirmacao.S || opcaoDoCardapio.getRecomendado() == Confirmacao.N,
	            "O campo recomendado só pode receber os valores 'S' ou 'N'.");

	    Long qtdeDeOpcoes = repository.contarPor(opcaoDoCardapio.getOpcao().getId(), opcaoDoCardapio.getCardapio().getId());
	    Preconditions.checkArgument(qtdeDeOpcoes == 0, "Já existe uma opção com o mesmo código no cardápio.");
	    
	    Preconditions.checkNotNull(opcaoDoCardapio.getSecao(), "A seção é obrigatória.");
	    Preconditions.checkArgument(!opcaoDoCardapio.getSecao().getStatus().equals("A"), "A seção deve estar ativa.");
	    
	    opcaoDoCardapio.setOpcao(opcaoEncontrada);
	    opcaoDoCardapio.setCardapio(cardapioEncontrado);
	    opcaoDoCardapio.setPreco(opcaoDoCardapio.getPreco());
	    opcaoDoCardapio.setRecomendado(opcaoDoCardapio.getRecomendado());
	    
	    OpcaoDoCardapio opcaoInserida = repository.saveAndFlush(opcaoDoCardapio);

	    return opcaoInserida;
	}

	
    @Override
    public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
        Preconditions.checkNotNull(opcao, "A opção é obrigatória.");
        Preconditions.checkNotNull(cardapio, "O cardápio é obrigatório.");
        Preconditions.checkArgument(opcao.isAtiva() && cardapio.isAtiva(), "A opção ou o cardápio estão inativos.");
        OpcaoDoCardapio opcaoDoCardapio = repository.buscarPor(opcao, cardapio);

        return opcaoDoCardapio;
    }

    public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcaoDoCardapio) {
        Preconditions.checkNotNull(opcaoDoCardapio.getId(), "O id da opção é obrigatório.");
        Preconditions.checkNotNull(opcaoDoCardapio.getOpcao(), "A opção é obrigatória.");
        Preconditions.checkNotNull(opcaoDoCardapio.getCardapio(), "O cardápio é obrigatório.");
        Preconditions.checkArgument(!opcaoDoCardapio.getOpcao().getStatus().equals("A"), "A opção deve estar ativa.");
        Preconditions.checkArgument(!opcaoDoCardapio.getCardapio().getStatus().equals("A"), "O cardápio deve estar ativo.");
        Preconditions.checkArgument(opcaoDoCardapio.getCardapio().getId().equals(opcaoDoCardapio.getCardapio().getId()), 
            "Não é permitido alterar o cardápio da opção.");
        
        OpcaoDoCardapio opcaoDoCardapioEncontrado = buscarPor(opcaoDoCardapio.getOpcao(), opcaoDoCardapio.getCardapio());
        
        if (opcaoDoCardapio.getOpcao().getPromocao().equals("S")) {
            Preconditions.checkNotNull(opcaoDoCardapio.getOpcao().getPercentalDeDesconto(), "O percentual de desconto deve ser fornecido.");
            BigDecimal precoComDesconto = opcaoDoCardapio.getPreco()
                    .subtract(opcaoDoCardapio.getPreco().multiply(opcaoDoCardapio.getOpcao().getPercentalDeDesconto()));
            
            opcaoDoCardapio.setPreco(precoComDesconto);
            
            Preconditions.checkArgument(opcaoDoCardapio.getOpcao().getNome().equals(opcaoDoCardapioEncontrado.getOpcao().getNome()),
                "Não é permitido alterar o nome da opção durante a promoção.");
            Preconditions.checkArgument(opcaoDoCardapio.getOpcao().getDescricao().equals(opcaoDoCardapioEncontrado.getOpcao().getDescricao()),
                "Não é permitido alterar a descrição da opção durante a promoção.");
        }
        
        Preconditions.checkArgument(opcaoDoCardapio.getPreco() != null && opcaoDoCardapio.getPreco().compareTo(BigDecimal.ZERO) > 0,
                "O preço deve ser maior que zero.");
        Preconditions.checkArgument(opcaoDoCardapio.getRecomendado().equals("S") || opcaoDoCardapio.getRecomendado().equals("N"),
                "O campo recomendado só pode receber os valores 'S' ou 'N'.");
        
        Long qtdeDeOpcoes = repository.contarPor(opcaoDoCardapio.getOpcao().getId(), opcaoDoCardapio.getCardapio().getId());
        Preconditions.checkArgument(qtdeDeOpcoes <= 1, "Já existe uma opção com o mesmo código no cardápio.");
        
        opcaoDoCardapioEncontrado.setPreco(opcaoDoCardapio.getPreco());
        opcaoDoCardapioEncontrado.setRecomendado(opcaoDoCardapio.getRecomendado());
        opcaoDoCardapioEncontrado.setStatus(opcaoDoCardapio.getStatus());
        
        if (opcaoDoCardapio.getSecao() != null) {
            Preconditions.checkArgument(!opcaoDoCardapio.getSecao().getStatus().equals("A"), "A seção deve estar ativa.");
            Preconditions.checkArgument(opcaoDoCardapio.getSecao().getId().equals(opcaoDoCardapioEncontrado.getSecao().getId()), 
                "Não é permitido alterar a seção da opção.");
        }
        
        OpcaoDoCardapio opcaoAtualizada = repository.saveAndFlush(opcaoDoCardapioEncontrado);

        return opcaoAtualizada;
    }
    
    private Cardapio getCardapioPor(Integer idDoCardapio) {
    	Cardapio cardapioEncontrado = cardapiosRepository.buscarPor(idDoCardapio);
    	
    	Preconditions.checkNotNull(cardapioEncontrado, "Não existe cardápio para o id informado.");
    	Preconditions.checkArgument(cardapioEncontrado.isAtiva(), "O cardápio informado inativo.");
    	
    	return cardapioEncontrado;
    }
    
    private Opcao getOpcaoPor(Integer idDaOpcao) {
		Opcao opcao = opcoesRepository.buscarPor(idDaOpcao);
		Preconditions.checkNotNull(opcao, "A opção '" + idDaOpcao + "' não foi encontrada.");
		Preconditions.checkArgument(opcao.isAtiva(), "A opção não está ativa.");
		return opcao;
	}


}
