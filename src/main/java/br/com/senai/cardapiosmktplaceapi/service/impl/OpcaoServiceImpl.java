package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

@Service
public class OpcaoServiceImpl implements OpcaoService {
	
	@Override
	public Opcao salvar(Opcao opcao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Opcao> listarPor(Categoria categoria, Restaurante restaurante, Pageable paginacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categoria buscarPor(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatusPor( Integer id, Status status) {
		// TODO Auto-generated method stub

	}

	@Override
	public Opcao excluirPor(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
