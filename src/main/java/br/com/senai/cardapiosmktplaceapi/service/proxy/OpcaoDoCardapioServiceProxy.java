package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceProxy implements OpcaoDoCardapioService {
	
	@Autowired
	@Qualifier("opcaoDoCardapioServiceImpl")
	private OpcaoDoCardapioService service;

	@Override
	public OpcaoDoCardapio inserir(NovaOpcaoCardapio novaOpcaoCardapio) {
		return service.inserir(novaOpcaoCardapio);
	}

	@Override
	public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio) {
		return service.buscarPor(opcao, cardapio);
	}

	@Override
	public void atualizar(OpcaoDoCardapio opcaoDoCardapio) {
		this.service.atualizar(opcaoDoCardapio);
	}

}
