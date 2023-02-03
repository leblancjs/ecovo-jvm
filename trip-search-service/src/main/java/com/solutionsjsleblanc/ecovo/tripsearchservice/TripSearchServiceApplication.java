package com.solutionsjsleblanc.ecovo.tripsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.solutionsjsleblanc.ecovo.tripsearchservice")
public class TripSearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripSearchServiceApplication.class, args);
    }
}
