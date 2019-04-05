package me.seungwoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaStudyApplication.class, args);
    }

    /*@Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            userRepository.save(new User("seungwoo", "seung0000",
                    "seungwoo@test.co.kr", LocalDate.now()));
            List<User> userList = userRepository.findAll();
            System.out.println(userList);
        };
    }*/

}
