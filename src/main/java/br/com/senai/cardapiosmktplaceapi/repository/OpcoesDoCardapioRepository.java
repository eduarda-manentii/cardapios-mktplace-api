package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;

@Repository
public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, Integer> {
	
	@Query(value = "SELECT o FROM OpcaoDoCardapio o "
			+ "JOIN FETCH o.opcao op "
			+ "JOIN FETCH op.secao s "
			+ "WHERE o.opcao = :opcao "
			+ "AND o.cardapio = :cardapio "
			+ "AND op.status = 'A' "
			+ "AND o.cardapio.status = 'A'")
	public OpcaoDoCardapio buscarPor(OpcaoDoCardapio opcaoDoCardapio, Cardapio cardapio);
	
	@Query(value = "UPDATE OpcaoDoCardapio odc "
	        + "SET odc.promocao = :promocao, "
	        + "    odc.preco = CASE WHEN :promocao = 'S' THEN odc.opcao.preco - (odc.opcao.preco * :percentualDesconto / 100) ELSE odc.opcao.preco END, "
	        + "    odc.recomendado = :recomendado "
	        + " WHERE odc.opcao = opcao "
	        + "  AND odc.opcao.ativo = true "
	        + "  AND odc.cardapio = cardapio "
	        + "  AND odc.cardapio.ativo = 'A' "
	        + "  AND odc.secao = secao "
	        + "  AND odc.secao.ativo = 'A' ")
	public OpcaoDoCardapio atualizar(OpcaoDoCardapio opcao);

	@Query(value = "SELECT Count(opc) FROM OpcaoDoCardapio opc WHERE opc.secao.id = :idDaSecao")
	public Long contarPor(Integer idDaSecao);

}
