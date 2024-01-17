package io.github.sql1freitas.Eshopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaDto {

    private String name;

    public MarcaDto(String name) {
        this.name = name;
    }
}
