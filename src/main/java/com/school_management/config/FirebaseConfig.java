package com.school_management.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Profile("!test")
public class FirebaseConfig {

    @Value("${firebase.credentials.path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
        //InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(firebaseConfigPath);
        if (serviceAccount == null) {
            throw new FileNotFoundException("Firebase service account file not found in classpath");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

    }

}
