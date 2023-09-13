package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.SecaoService;

@Service
public class SecaoServiceImpl implements SecaoService {
	
	@Autowired
	private SecoesRepository repository;
	
	@Autowired
	private OpcoesDoCardapioRepository opcoesDoCardapioRepository;
	
	@Override
	public Secao salvar(Secao secao) {
		Secao outraSecao = repository.buscarPor(secao.getNome());
		if (outraSecao != null) {
			if (outraSecao.isPersistida()) {
				Preconditions.checkArgument(outraSecao.equals(secao), 
						"O nome da seção já esta em uso.");
			}
			
		}
		Secao secaoSalva = repository.save(secao);
		return secaoSalva;
	}

	@Override
	public Secao buscarPor(Integer id) {
		Secao secaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(secaoEncontrada, "Não foi encontrada seção para o id informado");
		Preconditions.checkArgument(secaoEncontrada.isAtiva(), "A seção está inativa.");
		return secaoEncontrada;
	}

	@Override
	public Page<Secao> listarPor(String nome, Pageable paginacao) {
		return repository.listarPor("%" + nome + "%", paginacao);
	}

	@Override
	public void atualizarStatusPor(Integer id,  Status status) {
		Secao secaoEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(secaoEncontrada,
				"Não existe seção vinculada ao id informado.");
		Preconditions.checkArgument(secaoEncontrada.getStatus() != status,
				"O status já está salvo para a seção.");
		this.repository.atualizarStatusPor(id, status);

	}

	@Override
	public Secao excluirPor(Integer id) {
		Secao secaoParaExclusao = repository.buscarPor(id);
		Long qtdeDeOpcoesVinculadas = opcoesDoCardapioRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeOpcoesVinculadas == 0, "Não é possível remover pois existem opções do cardápio vinculados.");
		this.repository.deleteById(secaoParaExclusao.getId());
		return secaoParaExclusao;
	}

}
