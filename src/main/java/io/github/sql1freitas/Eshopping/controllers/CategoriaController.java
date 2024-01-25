package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.dto.CategoriaDto;
import io.github.sql1freitas.Eshopping.dto.MarcaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;



    @PostMapping("/save")
    public ResponseEntity<CategoriaDto> save (@RequestBody Categoria categoria){

        CategoriaDto newCategoria = categoriaService.save(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<CategoriaDto>> listarTodos(@RequestParam (defaultValue = "0") int pagina,
                                                          @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<CategoriaDto> categoriaPage = categoriaService.listarTodos(PageRequest.of(pagina,tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(categoriaPage);
    }

    @GetMapping("/todos/produto/{name}")
    public ResponseEntity<Page<ProdutoDto>> listarTodos (@PathVariable String name,
                                                         @RequestParam (defaultValue = "0") int pagina,
                                                         @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = categoriaService.listarTodosProdutos(name, PageRequest.of(pagina,tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
    }
    @GetMapping("/todos/name/{name}")
    public ResponseEntity<Page<CategoriaDto>> listarCategoriaNome(@PathVariable String name,
                                                                  @RequestParam (defaultValue = "0") int pagina,
                                                                  @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<CategoriaDto> categoriaPage = categoriaService.listarCategoriaPorNome(name, PageRequest.of(pagina,tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(categoriaPage);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarMarca (@PathVariable Long id){
        categoriaService.excluirCategoria(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/alternar-status/{id}")
    public ResponseEntity<Void> alternarStatus(@PathVariable Long id){

        categoriaService.alternarStatus(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
