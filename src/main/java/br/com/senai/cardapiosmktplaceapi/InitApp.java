package br.com.senai.cardapiosmktplaceapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Endereco;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.impl.CategoriaServiceImpl;
import br.com.senai.cardapiosmktplaceapi.service.impl.RestauranteServiceImpl;

@SpringBootApplication
public class InitApp {

    public static void main(String[] args) {
        SpringApplication.run(InitApp.class, args);
    }
    
    @Autowired
    private RestauranteServiceImpl restauranteService;
    
    @Autowired
    private CategoriaServiceImpl categoriaService;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
        	
        	System.out.println("Iniciando os testes...");

          	 // Exemplo de teste para inserir um restaurante
       	Restaurante restaurante = new Restaurante();
               	Endereco endereco = new Endereco();
               	endereco.setBairro("Bairro do restaurante");
               	endereco.setCidade("Cidade do restaurante");
               	endereco.setComplemento("Complemento do restaurante");
               	endereco.setLogradouro("Logradouro do restaurante");
               	restaurante.setEndereco(endereco);
                   restaurante.setNome("Nome do Restaurante");
                   restaurante.setStatus(Status.A);
                   restaurante.setCategoria(obtenhaCategoria()); 
                   restaurante.setDescricao("Descrição do restaurante");
                   Restaurante restauranteSalvo = restauranteService.salvar(restaurante);
                   System.out.println("Novo restaurante salvo: " + restauranteSalvo);
                   System.out.println("############################################");


                   // Exemplo de teste para buscar um restaurante por código
                   Integer codigoRestaurante = 1; 
                   Restaurante restauranteEncontrado = restauranteService.buscarPor(codigoRestaurante);
                   System.out.println("Restaurante encontrado: " + restauranteEncontrado);
                   System.out.println("############################################");

                // Exemplo de teste para listar restaurantes por nome e categoria
                   String nomeRestaurante = "Dudinkas";
                   Pageable pageable = PageRequest.of(0, 15);
                   Page<Restaurante> restaurantes = restauranteService.listarPor(
                   		nomeRestaurante,
                   		categoriaService.buscarPor(39), 
                   		pageable);
                   System.out.println("Restaurantes encontrados: " + restaurantes.getContent());
                   System.out.println("############################################");


                   // Exemplo de teste para atualizar o status de um restaurante
                   Integer codigoRestauranteParaAtualizar = 1;
                   Status novoStatus = Status.I; 
                   restauranteService.atualizarStatusPor(codigoRestauranteParaAtualizar, novoStatus);
                   System.out.println("Restaurante atualizado!");
                   System.out.println("############################################");

                   // Exemplo de teste para excluir um restaurante
                   Integer codigoRestauranteParaExcluir = 1; 
                   Restaurante restauranteExcluido = restauranteService.excluirPor(codigoRestauranteParaExcluir);
                   System.out.println("Restaurante excluído: " + restauranteExcluido);
                   System.out.println("############################################");

                   System.out.println("Testes concluídos.");

        };
        
    }
    
    private static Categoria obtenhaCategoria() {
        Categoria categoria = new Categoria();
        
        categoria.setId(150);
        return categoria;
    }
}

