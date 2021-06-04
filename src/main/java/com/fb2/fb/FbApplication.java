package com.fb2.fb;

import com.fb2.fb.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class FbApplication {

    public static void main(String[] args) {
        SpringApplication.run(FbApplication.class, args);
    }

}
