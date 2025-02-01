package com.lokapos;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class LokaposApplication {
    public static void initializeWithDefaultCredentials() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("firebase.json").getInputStream()))
                .build();
        System.out.println("FIREBASE INITIALIZED");
        FirebaseApp.initializeApp(options);
    }

    public static void main(String[] args) throws IOException {
        initializeWithDefaultCredentials();
        SpringApplication.run(LokaposApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

