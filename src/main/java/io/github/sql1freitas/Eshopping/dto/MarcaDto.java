package io.github.sql1freitas.Eshopping.dto;

import io.github.sql1freitas.Eshopping.services.MarcaService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcaDto {

    private String name;

    public MarcaDto(String name) {
        this.name = name;
    }

    public MarcaDto() {
    }
}
