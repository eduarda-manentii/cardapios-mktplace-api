package br.com.senai.cardapiosmktplaceapi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
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
        	
        	 // Exemplo de teste para buscar uma opção por código
            Integer codigoOpcao = 205;
            Opcao opcaoEncontrada = opcaoService.buscarPor(codigoOpcao);
            System.out.println("Opção encontrada: " + opcaoEncontrada);

        };
        
    }
}
