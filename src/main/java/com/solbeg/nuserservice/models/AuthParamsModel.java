package com.solbeg.nuserservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthParamsModel {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
