package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceImp implements OpcaoDoCardapioService {

    @Autowired
    private OpcoesDoCardapioRepository repository;

    @Override
    public OpcaoDoCardapio inserir(NovaOpcaoCardapio novaOpcaoCardapio) {
        return null;
    }

    @Override
    public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
        Preconditions.checkNotNull(opcao, "A opção é obrigatória.");
        Preconditions.checkNotNull(cardapio, "O cardápio é obrigatório.");
        Preconditions.checkArgument(opcao.isAtiva() && cardapio.isAtiva(), "A opção ou o cardápio estão inativos.");

        OpcaoDoCardapio opcaoDoCardapio = repository.buscarPor(opcao, cardapio);
        Preconditions.checkNotNull(opcaoDoCardapio, "Não foi encontrada a opção vinculada ao código do cardápio e opção informados.");

        return opcaoDoCardapio;
    }

    @Override
    public void atualizar(OpcaoDoCardapio opcaoDoCardapio) {
    }

    private Opcao getOpcaoPor(Integer idDaOpcao) {
        return null;
    }
}
