package io.github.sql1freitas.Eshopping;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.repositories.CategoriaRepository;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import io.github.sql1freitas.Eshopping.services.ProdutoService;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProdutoServiceTest {

    @Test
    public void testSave() {

        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
        CategoriaRepository categoriaRepository = Mockito.mock(CategoriaRepository.class);
        MarcaRepository marcaRepository = Mockito.mock(MarcaRepository.class);
        Assemble assemble = Mockito.mock(Assemble.class);

        ProdutoService produtoService = new ProdutoService(produtoRepository, categoriaRepository, marcaRepository, assemble);

        Produto produto = new Produto();
        Long idCategoria = 1L;
        Long idMarca = 2L;

        Mockito.when(categoriaRepository.findById(idCategoria)).thenReturn(Optional.of(new Categoria()));
        Mockito.when(marcaRepository.findById(idMarca)).thenReturn(Optional.of(new Marca()));
        Mockito.when(assemble.produtoParaDto(produto)).thenReturn(new ProdutoDto());

        ProdutoDto savedProdutoDto = produtoService.save(produto, idCategoria, idMarca);

        Mockito.verify(produtoRepository).save(produto);
        Mockito.verify(assemble).produtoParaDto(produto);

        assertNotNull(savedProdutoDto);
    }

}

