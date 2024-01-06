package org.sparta.hanghae99lv3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Hanghae99Lv3Application {

    public static void main(String[] args) {
        SpringApplication.run(Hanghae99Lv3Application.class, args);
    }

}
