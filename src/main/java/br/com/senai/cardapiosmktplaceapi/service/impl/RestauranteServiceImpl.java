package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class RestauranteServiceImpl implements RestauranteService {
	
	@Autowired
	private RestaurantesRepository repository;
	
	@Autowired @Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Autowired
	private CardapiosRepository cardapioRepository;

	@Override
	public Restaurante salvar(Restaurante restaurante) {
		Restaurante outroRestaurante = repository.buscarPor(restaurante.getNome());
		if (outroRestaurante != null) {
			if (restaurante.isPersistido()) {
				Preconditions.checkArgument(outroRestaurante.equals(restaurante), 
						"O nome do restaurante já esta em uso.");
			}
			
		}
		this.categoriaService.buscarPor(restaurante.getCategoria().getId());
		Restaurante restauranteSalvo = repository.save(restaurante);
		return restauranteSalvo;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Restaurante restauranteEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(restauranteEncontrado,
				"Não existe restaurante vinculada ao id informado.");
		Preconditions.checkArgument(restauranteEncontrado.getStatus() != status,
				"O status já está salvo para o restaurante.");
		this.repository.atualizarPor(id, status);

	}

	@Override
	public Page<Restaurante> listarPor(String nome, Categoria categoria, Pageable paginacao) {
		return repository.listarPor("%" + nome + "%", categoria, paginacao);
	}

	@Override
	public Restaurante buscarPor(Integer id) {
		Restaurante restauranteEncontrado  = repository.buscarPor(id);
		Preconditions.checkNotNull(restauranteEncontrado, "Não foi encontrado restaurante para o id informado");
		Preconditions.checkArgument(restauranteEncontrado.isAtiva(), "O restaurante está inativo.");
		return restauranteEncontrado;
	}

	@Override
	public Restaurante excluirPor(Integer id) {
		Restaurante restauranteParaExclusao = buscarPor(id);
		Long qtdeDeCardapiosVinculados = cardapioRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeCardapiosVinculados == 0, "Não é possível remover pois existem cardapios vinculados.");
		this.repository.deleteById(restauranteParaExclusao.getId());
		return restauranteParaExclusao;
	}

}
