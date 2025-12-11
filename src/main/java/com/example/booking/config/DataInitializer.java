package com.example.booking.config;

import com.example.booking.entity.User;
import com.example.booking.enums.UserRole;
import com.example.booking.enums.UserStatus;
import com.example.booking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to initialize default data
 * Creates a default Super Admin user on application startup
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Check if super admin already exists
            if (userRepository.findByUsername("superadmin").isEmpty()) {
                // Create default super admin user
                User superAdmin = new User();
                superAdmin.setFirstName("Super");
                superAdmin.setLastName("Admin");
                superAdmin.setEmail("superadmin@booking.com");
                superAdmin.setUsername("superadmin");
                superAdmin.setPassword("admin123"); // TODO: Use encrypted password in production
                superAdmin.setPhoneNumber("1234567890");
                superAdmin.setRole(UserRole.SUPER_ADMIN);
                superAdmin.setStatus(UserStatus.ACTIVE);

                userRepository.save(superAdmin);
                
                System.out.println("========================================");
                System.out.println("Default Super Admin Created:");
                System.out.println("Username: superadmin");
                System.out.println("Password: admin123");
                System.out.println("Email: superadmin@booking.com");
                System.out.println("========================================");
            }
        };
    }
}

