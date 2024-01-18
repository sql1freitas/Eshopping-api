package io.github.sql1freitas.Eshopping;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.CategoriaDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.repositories.CategoriaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import io.github.sql1freitas.Eshopping.services.CategoriaService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoriaServiceTest {

    @Test
    public void testSave() {

        CategoriaRepository categoriaRepository = Mockito.mock(CategoriaRepository.class);
        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
        Assemble assemble = Mockito.mock(Assemble.class);

        CategoriaService categoriaService = new CategoriaService(categoriaRepository, produtoRepository, assemble);

        Categoria categoria = new Categoria();

        Mockito.when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Mockito.when(assemble.categoriaDto(categoria)).thenReturn(new CategoriaDto());

        CategoriaDto savedCategoriaDto = categoriaService.save(categoria);

        Mockito.verify(categoriaRepository).save(categoria);
        Mockito.verify(assemble).categoriaDto(categoria);

        assertNotNull(savedCategoriaDto);
    }

}
