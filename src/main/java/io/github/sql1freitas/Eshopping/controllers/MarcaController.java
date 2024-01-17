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

    @GetMapping("/todos/{id}")
    public ResponseEntity<List<Produto>> listarTodos (@PathVariable Long id){

        List<Produto> produtoList = marcaService.listarTodos(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }

}
