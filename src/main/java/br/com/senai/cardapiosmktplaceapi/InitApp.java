package br.com.senai.cardapiosmktplaceapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TiposDeCategoria;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.impl.CategoriaServiceImpl;

@SpringBootApplication
public class InitApp {

    @Autowired
    private CategoriaServiceImpl categoriaService;

    public static void main(String[] args) {
        SpringApplication.run(InitApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Iniciando os testes...");

            // Exemplo de teste para salvar uma categoria
            Categoria categoria = new Categoria();
            categoria.setNome("Nome da Categoria");
            categoria.setStatus(Status.A);
            categoria.setTipo(TiposDeCategoria.RESTAURANTE);
            Categoria categoriaSalva = categoriaService.salvar(categoria);
            System.out.println("Nova categoria salva: " + categoriaSalva);
            

            // Exemplo de teste para buscar uma categoria por ID
            Integer idCategoria = 141; // Substitua pelo ID correto
            Categoria categoriaEncontrada = categoriaService.buscarPor(idCategoria);
            System.out.println("Categoria encontrada: " + categoriaEncontrada);

            
            // Exemplo de teste para atualizar o status de uma categoria
            Integer idCategoriaParaAtualizar = 141; // Substitua pelo ID correto
            Status novoStatus = Status.I; // Substitua pelo status correto
            categoriaService.atualizarStatusPor(idCategoriaParaAtualizar, novoStatus);
            System.out.println("Categoria atualizada!");


            // Exemplo de teste para listar categorias
            String nomeCategoria = "Categoria"; // Substitua pelo nome correto
            Page<Categoria> categorias = categoriaService.listarPor(
                nomeCategoria, Status.A, TiposDeCategoria.OPCAO, Pageable.unpaged());
            System.out.println("Categorias encontradas: " + categorias.getContent());

            // Exemplo de teste para excluir uma categoria
            Integer idCategoriaParaExcluir = 3; // Substitua pelo ID correto
            Categoria categoriaExcluida = categoriaService.excluirPor(idCategoriaParaExcluir);
            System.out.println("Categoria excluída: " + categoriaExcluida);

            System.out.println("Testes concluídos.");
        };
    }
}

