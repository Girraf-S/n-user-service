package com.solbeg.nuserservice.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 12)
    @Pattern(regexp = "(?=.+[a-z])|(?=.+[A-Z])|(?=.+[1-9]).+")
    private String password;

}
