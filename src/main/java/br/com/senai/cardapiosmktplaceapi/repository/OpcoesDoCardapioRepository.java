package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, Integer> {
	
	@Query(value = "SELECT o FROM OpcaoDoCardapio o "
			+ "JOIN FETCH o.opcao op "
			+ "WHERE o.opcao = :opcao "
			+ "AND o.cardapio = :cardapio "
			+ "AND op.status = 'A' "
			+ "AND o.cardapio.status = 'A'")
	public OpcaoDoCardapio buscarPor(Opcao opcao, Cardapio cardapio);
	
//	@Query(value = "UPDATED OpcaoDoCardapio o SET o.cardapio ")
//	public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcao);

	@Query(value = "SELECT Count(opc) FROM OpcaoDoCardapio opc WHERE opc.secao.id = :idDaSecao")
	public Long contarPor(Integer idDaSecao);

}
