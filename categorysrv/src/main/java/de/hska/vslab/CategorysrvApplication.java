package de.hska.vslab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class CategorysrvApplication {


    @Autowired
    private CategoryRepo categoryRepository;

    @PostConstruct
    public void generateTestData() {
        categoryRepository.save(new Category("Kategorie 1"));
        categoryRepository.save(new Category("Kategorie 2"));
        categoryRepository.save(new Category("Kategorie 3"));
    }

	public static void main(String[] args) {
		SpringApplication.run(CategorysrvApplication.class, args);
	}
}
