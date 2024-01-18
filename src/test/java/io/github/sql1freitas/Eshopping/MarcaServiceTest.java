package io.github.sql1freitas.Eshopping;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.MarcaDto;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import io.github.sql1freitas.Eshopping.services.MarcaService;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MarcaServiceTest {


    @Test
    public void testSave() {

        MarcaRepository marcaRepository = Mockito.mock(MarcaRepository.class);
        ProdutoRepository produtoRepository = Mockito.mock(ProdutoRepository.class);
        Assemble assemble = Mockito.mock(Assemble.class);

        MarcaService marcaService = new MarcaService(marcaRepository, produtoRepository, assemble);

        Marca marca = new Marca();

        Mockito.when(marcaRepository.save(marca)).thenReturn(marca);
        Mockito.when(assemble.marcaParaDto(marca)).thenReturn(new MarcaDto());

        MarcaDto savedMarcaDto = marcaService.save(marca);

        Mockito.verify(marcaRepository).save(marca);
        Mockito.verify(assemble).marcaParaDto(marca);

        assertNotNull(savedMarcaDto);
    }

}
