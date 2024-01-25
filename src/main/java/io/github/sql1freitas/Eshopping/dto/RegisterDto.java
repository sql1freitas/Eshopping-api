package io.github.sql1freitas.Eshopping.dto;


import io.github.sql1freitas.Eshopping.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
