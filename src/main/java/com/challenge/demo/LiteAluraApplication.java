package com.challenge.demo;

import org.springframework.beans.factory.annotation.Autowired; // Para injeção
import org.springframework.boot.CommandLineRunner; // Para rodar o menu ao iniciar
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteAluraApplication implements CommandLineRunner {

	@Autowired // Injeta a instância de Principal
	private Principal principal;

	public static void main(String[] args) {
		SpringApplication.run(LiteAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Quando a aplicação Spring Boot iniciar, este método será chamado.
		// Aqui você chama o método que exibe seu menu interativo.
		principal.exibeMenu();
	}
}