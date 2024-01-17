package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/save/{idCategoria}/{idMarca}")
    public ResponseEntity<Produto> save(@RequestBody Produto produto,
                                       @PathVariable Long idCategoria,
                                       @PathVariable Long idMarca) {

        Produto newProduto = produtoService.save(produto,idCategoria,idMarca);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduto);
    }
}
