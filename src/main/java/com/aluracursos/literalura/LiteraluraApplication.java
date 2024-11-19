package com.aluracursos.literalura;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.principal.Principal;
import com.aluracursos.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\nIniciando la aplicacion...\n");
		Principal principal = new Principal(repository);
		principal.muestraElMenu();
	}
}
