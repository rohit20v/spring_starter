package it.objectmethod.esercizi.spring_starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStarterApplication.class, args);
    }
//
//    @Bean
//    CommandLineRunner logAllBeans(ApplicationContext context) {
//        return args -> {
//            for (String beanName : context.getBeanDefinitionNames()) {
//                System.out.printf("bean -> %s\n", beanName);
//            }
//        };
//    }
}
