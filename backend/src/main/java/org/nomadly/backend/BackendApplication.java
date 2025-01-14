package org.nomadly.backend;

import jakarta.annotation.PostConstruct;
import org.nomadly.backend.initDB.InitDbCategories;
import org.nomadly.backend.initDB.InitDbLanguages;
import org.nomadly.backend.initDB.InitDbLocations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    InitDbCategories initDbCategories;
    InitDbLanguages initDbLanguages;
    InitDbLocations initDbLocations;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @PostConstruct
    public void seedDatabase() {
//        initDbCategories.seedDBCategory();
//        initDbLanguages.seedDBLanguage();
//        initDbLocations.seedDBLocations();
    }

}
