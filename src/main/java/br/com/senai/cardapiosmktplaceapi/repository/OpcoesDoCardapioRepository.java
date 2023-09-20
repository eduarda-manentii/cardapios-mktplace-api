package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, Integer> {
	

	@Query(value = "SELECT Count(opc) FROM OpcaoDoCardapio opc WHERE opc.secao.id = :idDaSecao")
	public Long contarPor(Integer idDaSecao);

}
