package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceImp implements OpcaoDoCardapioService {

    @Override
    public OpcaoDoCardapio inserir(NovaOpcaoCardapio novaOpcaoCardapio) {
        return null;
    }

    @Override
    public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
        return null;
    }

    @Override
    public void atualizar(OpcaoDoCardapio opcaoDoCardapio) {
    }

}
