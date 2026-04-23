package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record CreateUserCommand(
    @NotBlank String id,
    @Size(min = 3) String name,
    @Email String email,
    @Size(min = 8) String password,
    @NotBlank String role)
{

}
