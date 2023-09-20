package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.CarregarOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

@Service
public class OpcaoServiceImpl implements OpcaoService {
	
	@Autowired
	private OpcoesRepository repository;
	
	@Autowired @Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Autowired
	private CardapiosRepository cardapioRepository;
	
	@Override
	public Opcao salvar(Opcao opcao) {
	    Opcao outraOpcao = repository.buscarPorNomeRestaurante(opcao.getNome(), opcao.getRestaurante().getId());
		if (outraOpcao != null && outraOpcao.isPersistido()) {
			Preconditions.checkArgument(outraOpcao.equals(opcao)
					&& outraOpcao.getRestaurante().getId() == opcao.getRestaurante().getId(), 
					"O nome da opção já existe nesse restaurante.");
		}
		Opcao opcaoSalva = repository.save(opcao);
		return opcaoSalva;
	}

	@Override
	public Page<CarregarOpcao> listarPor(String nome, Categoria categoria, Restaurante restaurante, Pageable paginacao) {
	    Preconditions.checkArgument(nome != null && nome.length() >= 3, "O nome informado deve ter pelo menos 3 caracteres.");
	    
	    boolean categoriaOuRestauranteInformados = categoria != null || restaurante != null;
	    Preconditions.checkArgument(categoriaOuRestauranteInformados, "Pelo menos a categoria ou o restaurante devem ser informados.");

	    return repository.listarPor(nome, categoria, restaurante, paginacao);
	}
	
	@Override
	public Opcao buscarPor(Integer id) {
		Opcao opcaoEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoEncontrado, "Não foi encontrado opção para o Id informado.");
		Preconditions.checkArgument(opcaoEncontrado.isAtiva(), "O restaurante está inativo.");
		return opcaoEncontrado;
	}

	@Override @Transactional
	public void atualizarStatusPor(Integer id, Status status) {
		Opcao opcaoEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(opcaoEncontrado, "Não existe opção vinculada ao id informado.");
		Preconditions.checkArgument(opcaoEncontrado.getStatus() != status, "A status já está salva para o opção.");
		this.repository.atualizarPor(id, status);
	}

	@Override
	public Opcao excluirPor(Integer id) {
		Opcao opcaoParaExclusao = buscarPor(id);
		Long qtdeDeCardapiosVinculado = cardapioRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeCardapiosVinculado == 0, "Não é possível "
				+ " remover pois existem cardápios vinculados.");
		this.repository.deleteById(opcaoParaExclusao.getId());
		return opcaoParaExclusao;
	}

}
