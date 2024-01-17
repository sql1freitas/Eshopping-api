package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;



    @PostMapping("/save")
    public ResponseEntity<Categoria> save (@RequestBody Categoria categoria){

        Categoria newCategoria = categoriaService.save(categoria);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
    }
}
