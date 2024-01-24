package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<ProdutoDto>> listarTodos(@RequestParam (defaultValue = "0") int pagina,
                                                        @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = produtoService.listarTodos(PageRequest.of(pagina, tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
    }
    @GetMapping("/todos/nome/{name}")
    public ResponseEntity<Page<ProdutoDto>> buscarPorNome (@PathVariable String name,
                                                           @RequestParam (defaultValue = "0") int pagina,
                                                           @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = produtoService.buscarPorNome(name, PageRequest.of(pagina, tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
    }
    @GetMapping("/todos/rangeDeValor/{primeiroValor}/{segundoValor}")
    public ResponseEntity<Page<ProdutoDto>> buscarRangePreco(@PathVariable Double primeiroValor,
                                                             @PathVariable Double segundoValor,
                                                             @RequestParam (defaultValue = "0") int pagina,
                                                             @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = produtoService.buscarPorRangePreco(primeiroValor, segundoValor, PageRequest.of(pagina, tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
    }

    @GetMapping("/todos/marca/{name}")
    public ResponseEntity<Page<ProdutoDto>> buscarPorMarca(@PathVariable String name,
                                                           @RequestParam (defaultValue = "0") int pagina,
                                                           @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = produtoService.buscarPorMarca(name, PageRequest.of(pagina,tamanhoPagina));

       return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
    }

    @GetMapping("/todos/categoria/{name}")
    public ResponseEntity<Page<ProdutoDto>> buscarPorCategoria(@PathVariable String name,
                                                               @RequestParam (defaultValue = "0") int pagina,
                                                               @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = produtoService.buscarPorCategoria(name, PageRequest.of(pagina,tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
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
