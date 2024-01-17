package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.services.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marca")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;


    @PostMapping("/save")
    public ResponseEntity<Marca> save (@RequestBody Marca marca){

        Marca newMarca = marcaService.save(marca);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMarca);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Marca>> listarTodos(){

        List<Marca> marcaList = marcaService.listarTodas();

        return ResponseEntity.status(HttpStatus.OK).body(marcaList);
    }

    @GetMapping("/todos/produto/{id}")
    public ResponseEntity<List<Produto>> listarTodos (@PathVariable Long id){

        List<Produto> produtoList = marcaService.listarTodosProdutos(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }
    @GetMapping("/todos/marca/name/{name}")
    public ResponseEntity<List<Marca>> listarMarcasNome(@PathVariable String name){
         List<Marca> marcaList = marcaService.listarMarcaPorNome(name);

         return ResponseEntity.status(HttpStatus.OK).body(marcaList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarMarca (@PathVariable Long id){
        marcaService.excluirMarca(id);

        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/desativar/{id}")
    public ResponseEntity<Void> desabilitarMarca (@PathVariable Long id){
        marcaService.desabilitar(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<Void> habilitarMarca (@PathVariable Long id){
        marcaService.habilitar(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
