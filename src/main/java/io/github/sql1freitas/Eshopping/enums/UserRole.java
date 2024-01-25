package io.github.sql1freitas.Eshopping.enums;

import lombok.Getter;


public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");


    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
