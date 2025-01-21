package com.andycaicedo.comerciants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Comerciants-Application", version = "1.0", description = "Comerciants API"))
public class ComerciantsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComerciantsApplication.class, args);
	}

}
