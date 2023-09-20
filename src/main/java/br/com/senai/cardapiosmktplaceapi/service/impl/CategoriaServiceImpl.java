package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TiposDeCategoria;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import jakarta.transaction.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	private CategoriasRepository repository;
	
	@Autowired
	private RestaurantesRepository restauranteRepository;

	@Override
	public Categoria salvar(Categoria categoria) {
		Categoria outraCategoria = repository.buscarPor(
				categoria.getNome(), categoria.getTipo());
		if (outraCategoria != null && outraCategoria.isPersistido()) {
			Preconditions.checkArgument(outraCategoria.equals(categoria), 
					"O nome da categoria já está em uso");
		}
		Categoria categoriaSalva = repository.save(categoria); 
		return categoriaSalva;
	}

	@Override @Transactional
	public void atualizarStatusPor(Integer id, Status status) {
		Categoria categoriaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada,
				"Não existe categoria vinculada ao id informado.");
		Preconditions.checkArgument(categoriaEncontrada.getStatus() != status,
				"O status já está salvo para a categoria.");
		this.repository.atualizarPor(id, status);
 	}

	@Override
	public Page<Categoria> listarPor(String nome, Status status, TiposDeCategoria tipoDaCategoria, Pageable paginacao) {
		return repository.listarPor(nome + "%", status, tipoDaCategoria, paginacao);
	}

	@Override
	public Categoria buscarPor(Integer id) {
		Categoria categoriaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada, "Não foi encontrada categoria para o id informado");
		Preconditions.checkArgument(categoriaEncontrada.isAtiva(), "A categoria está inativa.");
		return categoriaEncontrada;
	}

	@Override
	public Categoria excluirPor(Integer id) {
		Categoria categoriaParaExlusao = buscarPor(id);
		Long qtdeDeRestaurantesVinculados = restauranteRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeRestaurantesVinculados == 0, "Não é possível remover pois existem restaurantes vinculados.");
		this.repository.deleteById(categoriaParaExlusao.getId());
		return categoriaParaExlusao;
	}

}
