package io.github.sql1freitas.Eshopping.dto;


import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class Assemble {

    public ProdutoDto produtoParaDto (Produto produto){

        return new ProdutoDto(produto.getName(), produto.getPrice(), produto.getQuantidadeEstoque());
    }

    public MarcaDto marcaParaDto (Marca marca){

        return new MarcaDto(marca.getName());
    }

    public CategoriaDto categoriaDto(Categoria categoria){

        return new CategoriaDto(categoria.getName());
    }
}
