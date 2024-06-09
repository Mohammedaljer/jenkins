package com.example.demo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Account {
    @Id
    private Long id;
    private String name;
    private String encryptedPassword; // This will be binary data (byte[])
    private String email;
    
}
