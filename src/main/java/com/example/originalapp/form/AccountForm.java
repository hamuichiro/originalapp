package com.example.originalapp.form;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.originalapp.validation.constraints.PasswordEquals;

import lombok.Data;

@Data
@PasswordEquals
public class AccountForm {

    @NotEmpty
    @Size(max = 100)
    private String name;

    @NotEmpty
    @Size(max = 20)
    private String password;

    @NotEmpty
    @Size(max = 20)
    private String passwordConfirmation;

}