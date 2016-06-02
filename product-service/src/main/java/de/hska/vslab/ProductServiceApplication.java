package de.hska.vslab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {


    @Autowired
    private ProductRepo userRepository;

    @PostConstruct
    public void generateTestData() {
        userRepository.save(new Product(1000L,"Alice","pwd1", "Admin"));
        userRepository.save(new Product(1001L,"Bob","pwd2", "Product"));
    }

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
}
