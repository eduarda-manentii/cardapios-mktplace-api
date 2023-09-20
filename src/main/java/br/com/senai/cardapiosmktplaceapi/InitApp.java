package br.com.senai.cardapiosmktplaceapi;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.impl.CategoriaServiceImpl;
import br.com.senai.cardapiosmktplaceapi.service.impl.OpcaoServiceImpl;
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
    
    @Autowired
    private OpcaoServiceImpl opcaoService;
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
        	
        	
            // Exemplo de teste para inserir uma opção
               Opcao opcao = new Opcao();
               opcao.setNome("Nome dudica");
               opcao.setCategoria(categoriaService.buscarPor(40));
               opcao.setRestaurante(restauranteService.buscarPor(33));
               opcao.setStatus(Status.A);
               opcao.setPromocao(Confirmacao.N);
				BigDecimal divisor = new BigDecimal(20);
               opcao.setPercentalDeDesconto(divisor); 
               Opcao opcaoSalva = opcaoService.salvar(opcao);
               System.out.println("Nova opção salva: " + opcaoSalva);


        };
        
    }
}
