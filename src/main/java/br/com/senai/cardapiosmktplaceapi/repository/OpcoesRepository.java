package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.dto.CarregarOpcao;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao, Integer> {
	
	@Query(value = "SELECT o FROM Opcao o JOIN FETCH o.categoria JOIN FETCH o.restaurante WHERE o.id = :id")
	public Opcao buscarPor(Integer id);
	
	@Query(value = "SELECT o " +
	        "FROM Opcao o " +
	        "WHERE (:nome IS NOT NULL) " +
	        "AND ((:categoria IS NULL) OR (o.categoria = :categoria)) " +
	        "AND ((:restaurante IS NULL) OR (o.restaurante = :restaurante)) " +
	        "ORDER BY o.nome",
	        countQuery = "SELECT COUNT(o) FROM Opcao o " +
	                     "WHERE (:nome IS NOT NULL) " +
	                     "AND ((:categoria IS NULL) OR (o.categoria = :categoria)) " +
	                     "AND ((:restaurante IS NULL) OR (o.restaurante = :restaurante)) ")
	public Page<CarregarOpcao> listarPor(String nome, Categoria categoria, Restaurante restaurante, Pageable paginacao);
	
	@Modifying
	@Query(value = "UPDATE Opcao o SET o.status = :status WHERE r.id = :id")
	public void atualizarPor(Integer id, Status status);
}
