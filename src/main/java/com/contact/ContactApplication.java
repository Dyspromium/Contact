package com.contact;

import com.contact.entity.Contact;
import com.contact.repository.ContactRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ContactRepository repository) {
		return (args) -> {
			// save a few customers
			Contact c = new Contact("Jack");
			c.addMail("test");
			repository.save(c);


		};
	}

}
