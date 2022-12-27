package ua.com.foxminded.universitycms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ua.com.foxminded.universitycms")
public class UnivesityApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(UnivesityApplication.class, args);
		
	}

}
