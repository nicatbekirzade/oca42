package com.example.oca42.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {

    @NotBlank
    @Size(min = 4, max = 16, message = "username 4 karakterden cox olmalidir")
    private String username;

    @NotBlank
    @Size(min = 4, max = 16)
    private String password;


    @Size(max = 200)
    private String description;
}
