package io.github.sql1freitas.Eshopping.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDto {

    private String name;

    private Double price;

    private Integer quantidadeEstoque;

    public ProdutoDto(String name, Double price, Integer quantidadeEstoque) {
        this.name = name;
        this.price = price;
        this.quantidadeEstoque = quantidadeEstoque;
    }
}


