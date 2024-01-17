package io.github.sql1freitas.Eshopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDto {

    private String name;

    public CategoriaDto(String name) {
        this.name = name;
    }
}
