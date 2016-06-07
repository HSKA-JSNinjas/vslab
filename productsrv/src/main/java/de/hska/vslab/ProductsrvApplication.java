package de.hska.vslab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsrvApplication {


    @Autowired
    private ProductRepo productRepository;

    @PostConstruct
    public void generateTestData() {

        productRepository.save(new Product("Produkt 1", 9.99, 1, "bla bla bla1"));
        productRepository.save(new Product("Produkt 2", 10.99, 2, "bla bla bla2"));
        productRepository.save(new Product("Produkt 3", 11.99, 1, "bla bla bla3"));
    }

	public static void main(String[] args) {
		SpringApplication.run(ProductsrvApplication.class, args);
	}
}
