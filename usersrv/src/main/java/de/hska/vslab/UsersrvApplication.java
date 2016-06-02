package de.hska.vslab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
public class UsersrvApplication {


    @Autowired
    private UserRepo userRepository;

    @PostConstruct
    public void generateTestData() {
        userRepository.save(new User(1000L,"Alice","pwd1"));
        userRepository.save(new User(1001L,"Bob","pwd2"));
    }

	public static void main(String[] args) {
		SpringApplication.run(UsersrvApplication.class, args);
	}
}
