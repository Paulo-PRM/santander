package br.com.stefanini.santander.santander;

import br.com.stefanini.santander.santander.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SantanderApplication implements CommandLineRunner {

    private static final String INICIANDO_CONSULTA = "Iniciando a consulta de CEPs.";
    private static final String FINALIZANDO_CONSULTA = "Consulta de CEPs finalizada.";

    @Autowired
    Principal principal;

	public static void main(String[] args) {
        SpringApplication.run(SantanderApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println(INICIANDO_CONSULTA);
        principal.getCep();
        System.out.println(FINALIZANDO_CONSULTA);

    }

}
