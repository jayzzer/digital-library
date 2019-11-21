package com.pupsiki.digitallibrary;

import com.pupsiki.digitallibrary.models.Customer;
import com.pupsiki.digitallibrary.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DigitalLibraryApplication {
    private static final Logger logger = LoggerFactory.getLogger(DigitalLibraryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DigitalLibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            repository.save(new Customer("Jack", "Vasyov"));
            repository.save(new Customer("Lyova", "Hrushev"));
            repository.save(new Customer("Lena", "Grechneva"));
            repository.save(new Customer("Dima", "Hrushev"));

            logger.info("Customers found with findAll():");
            logger.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                logger.info(customer.toString());
            }
            logger.info("");

            Customer oneCustomer = repository.findById(1);
            logger.info("Customer found with id 1:");
            logger.info("-------------------------------");
            logger.info(oneCustomer.toString());
            logger.info("");

            logger.info("Customers found by lastName");
            logger.info("-------------------------------");
            for (Customer customer : repository.findByLastName("Hrushev")) {
                logger.info(customer.toString());
            }

            logger.info("");
        };
    }
}
