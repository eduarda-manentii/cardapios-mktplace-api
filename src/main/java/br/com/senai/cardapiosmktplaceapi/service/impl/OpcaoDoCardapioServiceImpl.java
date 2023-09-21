package br.com.senai.cardapiosmktplaceapi.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceImpl implements OpcaoDoCardapioService {
	  
	@Autowired
	private OpcoesDoCardapioRepository repository;
	  
	@Autowired
	private CardapiosRepository cardapioRepository;

	    public OpcaoDoCardapio inserir(NovaOpcaoCardapio novaOpcaoCardapio) {
	           return  null;
	    }

    @Override
    public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
        Preconditions.checkNotNull(opcao, "A opção é obrigatória.");
        Preconditions.checkNotNull(cardapio, "O cardápio é obrigatório.");
        Preconditions.checkArgument(opcao.isAtiva() && cardapio.isAtiva(), "A opção ou o cardápio estão inativos.");

       // OpcaoDoCardapio opcaoDoCardapio = repository.buscarPor(opcao, cardapio);
        //Preconditions.checkNotNull(opcaoDoCardapio, "Não foi encontrada a opção vinculada ao código do cardápio e opção informados.");

        return null;
    }

//	@Override
//	public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcaoDoCardapio) {
//		Cardapio cardapio = cardapioRepository.buscarPor(opcaoDoCardapio.getCardapio().getId());
//		OpcaoDoCardapio opcao = repository.buscarPor(opcaoDoCardapio, cardapio);
//		opcaoDoCardapio.setOpcao(opcaoDoCardapio.getOpcao());
//		opcaoDoCardapio.setRecomendado(opcaoDoCardapio.getRecomendado());
//		opcaoDoCardapio.setPreco(opcaoDoCardapio.getPreco());
//		opcaoDoCardapio.setSecao(opcaoDoCardapio.getSecao());
//		OpcaoDoCardapio opcaoCardapioAtualizado = repository.saveAndFlush(opcao);
//		return buscarPor(opcaoCardapioAtualizado.getOpcao(), cardapio);
//		
//	}
    
}
