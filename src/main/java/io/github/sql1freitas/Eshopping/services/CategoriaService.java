package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.CategoriaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.exceptions.EntidadeDesabilitadaException;
import io.github.sql1freitas.Eshopping.exceptions.EntidadeHabilitadaException;
import io.github.sql1freitas.Eshopping.repositories.CategoriaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final Assemble assemble;


    public CategoriaDto save (Categoria categoria){

        Categoria newCategoria = categoriaRepository.save(categoria);

        return assemble.categoriaDto(newCategoria);
    }

    public List<CategoriaDto> listarTodos (){

        return categoriaRepository.findAll(PageRequest.of(0,10))
                .stream()
                .filter(categoria -> categoria.getHabilitar().equals(true))
                .map(assemble::categoriaDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoDto> listarTodosProdutos (String name){

        Categoria categoria = categoriaRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        return produtoRepository.findByCategoria(categoria.getName())
                .stream()
                .filter(produto -> produto.getHabilitar().equals(true))
                .map(assemble::produtoParaDto)
                .collect(Collectors.toList());
    }

    public List<CategoriaDto> listarCategoriaPorNome (String name){

        return categoriaRepository.findByNameIgnoreCaseStartingWith(name)
                .stream()
                .filter(categoria -> categoria.getHabilitar().equals(true))
                .map(assemble::categoriaDto)
                .collect(Collectors.toList());
    }

    public void excluirCategoria (Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoriaRepository.deleteById(id);
    }


    public void alternarStatus(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        boolean novoStatus = !categoria.getHabilitar();

        produtoRepository.atualizarStatusProduto(id, novoStatus);
        log.info("A categoria foi {} com êxito: {}", novoStatus ? "habilitado" : "desabilitado", id);
    }
}
