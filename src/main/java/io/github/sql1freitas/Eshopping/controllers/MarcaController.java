package io.github.sql1freitas.Eshopping.controllers;

import io.github.sql1freitas.Eshopping.dto.MarcaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.services.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<MarcaDto> save (@RequestBody Marca marca){

        MarcaDto newMarca = marcaService.save(marca);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMarca);
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<MarcaDto>> listarTodos(@RequestParam (defaultValue = "0") int pagina,
                                                      @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<MarcaDto> marcaPage = marcaService.listarTodas(PageRequest.of(pagina,tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(marcaPage);
    }

    @GetMapping("/todos/produto/{name}")
    public ResponseEntity<Page<ProdutoDto>> listarTodos (@PathVariable String name,
                                                         @RequestParam (defaultValue = "0") int pagina,
                                                         @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<ProdutoDto> produtoPage = marcaService.listarTodosProdutos(name, PageRequest.of(pagina,tamanhoPagina));

        return ResponseEntity.status(HttpStatus.OK).body(produtoPage);
    }

    @GetMapping("/todos/name/{name}")
    public ResponseEntity<Page<MarcaDto>> listarMarcasNome(@PathVariable String name,
                                                           @RequestParam (defaultValue = "0") int pagina,
                                                           @RequestParam (defaultValue = "10") int tamanhoPagina){

        Page<MarcaDto> marcaPage = marcaService.listarMarcaPorNome(name, PageRequest.of(pagina,tamanhoPagina));

         return ResponseEntity.status(HttpStatus.OK).body(marcaPage);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarMarca (@PathVariable Long id){
        marcaService.excluirMarca(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/alternar-status/{id}")
    public ResponseEntity<Void> alternarStatus(@PathVariable Long id){

        marcaService.alternarStatus(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
