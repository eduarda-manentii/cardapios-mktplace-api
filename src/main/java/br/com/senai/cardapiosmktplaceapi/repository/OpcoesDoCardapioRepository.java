package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, Integer> {
	
	@Query(value = "SELECT r FROM Restaurante r " +
	        "JOIN FETCH r.categoria " +
	        "WHERE UPPER(r.nome) LIKE UPPER(:nome) " +
	        "AND ((:categoria IS NULL) OR (r.categoria = :categoria)) " +
	        "AND ((:restaurante IS NULL) OR (r = :restaurante)) " +
	        "ORDER BY r.nome",
	        countQuery = "SELECT COUNT(r) FROM Restaurante r " +
	                     "WHERE UPPER(r.nome) LIKE UPPER(:nome) " +
	                     "AND ((:categoria IS NULL) OR (r.categoria = :categoria)) " +
	                     "AND ((:restaurante IS NULL) OR (r = :restaurante)) ")
	public Page<OpcaoDoCardapio> listarPorNome(String nome, Categoria categoria, Restaurante restaurante, Pageable paginacao);


	@Query(value = "SELECT r FROM Restaurante r " +
	        "JOIN FETCH r.categoria " +
	        "WHERE ((:categoria IS NULL) OR (r.categoria = :categoria)) " +
	        "ORDER BY r.nome",
	        countQuery = "SELECT COUNT(r) FROM Restaurante r " +
	                     "WHERE ((:categoria IS NULL) OR (r.categoria = :categoria))")
	public Page<OpcaoDoCardapio> listarPorCategoria(Categoria categoria, Pageable paginacao);

	
	@Query(value = "SELECT r FROM Restaurante r " +
	        "JOIN FETCH r.categoria " +
	        "WHERE UPPER(r.nome) LIKE UPPER(:nome) " +
	        "AND ((:restaurante IS NULL) OR (r = :restaurante)) " +
	        "ORDER BY r.nome",
	        countQuery = "SELECT COUNT(r) FROM Restaurante r " +
	                     "WHERE UPPER(r.nome) LIKE UPPER(:nome) " +
	                     "AND ((:restaurante IS NULL) OR (r = :restaurante))")
	public Page<OpcaoDoCardapio> listarPorRestaurante(String nome, Restaurante restaurante, Pageable paginacao);

	@Query(value = "SELECT opc FROM OpcaoDoCardapio opc WHERE opc.id = :id")
	public OpcaoDoCardapio buscarPor(Integer id);
	
	@Modifying
	@Query(value = "UPDATE OpcaoDoCardapio opc SET opc.status = :status WHERE opc.id = :id")
	public void atualizarStatusPor(Integer id, Status status);
	
	@Query(value = "SELECT Count(opc) FROM OpcaoDoCardapio opc WHERE opc.secao.id = :idDaSecao")
	public Long contarPor(Integer idDaSecao);

}
