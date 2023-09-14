package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, Integer> {
	
	@Query(value = "SELECT opc FROM OpcaoDoCardapio opc WHERE opc.id = :id")
	public OpcaoDoCardapio buscarPor(Integer id);
	
	@Modifying
	@Query(value = "UPDATE OpcaoDoCardapio opc SET opc.status = :status WHERE opc.id = :id")
	public void atualizarStatusPor(Integer id, Status status);
	
	@Query(value = "SELECT Count(opc) FROM OpcaoDoCardapio opc WHERE opc.secao.id = :idDaSecao")
	public Long contarPor(Integer idDaSecao);

}
