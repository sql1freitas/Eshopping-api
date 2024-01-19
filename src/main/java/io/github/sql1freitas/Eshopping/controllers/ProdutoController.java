package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/save/{idCategoria}/{idMarca}")
    public ResponseEntity<ProdutoDto> save(@RequestBody Produto produto,
                                       @PathVariable Long idCategoria,
                                       @PathVariable Long idMarca) {

        ProdutoDto newProduto = produtoService.save(produto,idCategoria,idMarca);

        return ResponseEntity.status(HttpStatus.CREATED).body(newProduto);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoDto>> listarTodos(){
        List<ProdutoDto> produtoList = produtoService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }
    @GetMapping("/todos/nome/{name}")
    public ResponseEntity<List<ProdutoDto>> buscarPorNome (@PathVariable String name){
        List<ProdutoDto> produtoList = produtoService.buscarPorNome(name);

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }
    @GetMapping("/todos/rangeDeValor/{primeiroValor}/{segundoValor}")
    public ResponseEntity<List<ProdutoDto>> buscarRangePreco(@PathVariable Double primeiroValor,
                                                             @PathVariable Double segundoValor){
        List<ProdutoDto> produtoList = produtoService.buscarPorRangePreco(primeiroValor, segundoValor);

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }

    @GetMapping("/todos/marca/{name}")
    public ResponseEntity<List<ProdutoDto>> buscarPorMarca(@PathVariable String name){
       List<ProdutoDto> produtoList = produtoService.buscarPorMarca(name);

       return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }

    @GetMapping("/todos/categoria/{name}")
    public ResponseEntity<List<ProdutoDto>> buscarPorCategoria(@PathVariable String name){
        List<ProdutoDto> produtoList = produtoService.buscarPorCategoria(name);

        return ResponseEntity.status(HttpStatus.OK).body(produtoList);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id){
        produtoService.excluirProduto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/alternar-status/{id}")
    public ResponseEntity<Void> alternarStatus(@PathVariable Long id){

        produtoService.alternarStatus(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
