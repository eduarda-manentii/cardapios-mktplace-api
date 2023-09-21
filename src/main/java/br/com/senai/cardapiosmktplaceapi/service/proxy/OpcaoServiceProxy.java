package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.CarregarOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

@Service
public class OpcaoServiceProxy implements OpcaoService {
	
	@Autowired @Qualifier("opcaoServiceImpl")
	private OpcaoService serivce;

	@Override
	public Opcao salvar(Opcao opcao) {
		return serivce.salvar(opcao);
	}

	@Override
	public Page<CarregarOpcao> listarPor(String nome, 
			Categoria categoria, Restaurante restaurante, Pageable paginacao) {
		return listarPor(nome, categoria, restaurante, paginacao);
	}

	@Override
	public Opcao buscarPor(Integer id) {
		return serivce.buscarPor(id);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.serivce.atualizarStatusPor(id, status);
	}

	@Override
	public Opcao excluirPor( Integer id) {
		return serivce.excluirPor(id);
	}
	

}
