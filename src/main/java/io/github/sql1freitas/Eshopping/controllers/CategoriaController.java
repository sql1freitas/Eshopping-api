package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.dto.CategoriaDto;
import io.github.sql1freitas.Eshopping.dto.MarcaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.services.CategoriaService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<CategoriaDto>> listarTodos(){

        List<CategoriaDto> categoriaList = categoriaService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(categoriaList);
    }

    @GetMapping("/todos/produto/{id}")
    public ResponseEntity<List<ProdutoDto>> listarTodos (@PathVariable Long id){

        List<ProdutoDto> produtoList = categoriaService.listarTodosProdutos(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }
    @GetMapping("/todos/name/{name}")
    public ResponseEntity<List<CategoriaDto>> listarCategoriaNome(@PathVariable String name){
        List<CategoriaDto> categoriaList = categoriaService.listarCategoriaPorNome(name);

        return ResponseEntity.status(HttpStatus.OK).body(categoriaList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarMarca (@PathVariable Long id){
        categoriaService.excluirCategoria(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Void> desabilitarMarca (@PathVariable Long id){
        categoriaService.desabilitar(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> habilitarMarca (@PathVariable Long id){
        categoriaService.habilitar(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
