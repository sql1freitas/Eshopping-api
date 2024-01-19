package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.exceptions.EntidadeDesabilitadaException;
import io.github.sql1freitas.Eshopping.exceptions.EntidadeHabilitadaException;
import io.github.sql1freitas.Eshopping.exceptions.ValorInvalidoException;
import io.github.sql1freitas.Eshopping.repositories.CategoriaRepository;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final Assemble assemble;



    public ProdutoDto save(Produto produto, Long idCategoria, Long idMarca){

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));


        produto.setCategoria(categoria);
        produto.setMarca(marca);
        produto.setDataEntradaEstoque(LocalDateTime.now());

        categoria.getProdutos().add(produto);
        marca.getProdutos().add(produto);


        categoriaRepository.save(categoria);
        marcaRepository.save(marca);

        produtoRepository.save(produto);

        return assemble.produtoParaDto(produto);
    }

    public List<ProdutoDto> listarTodos (){
        return produtoRepository.findAll()
                .stream()
                .filter(produto -> produto.getHabilitar().equals(true))
                .map(assemble::produtoParaDto)
                .collect(Collectors.toList());
    }


    public List<ProdutoDto> buscarPorNome(String name){
        return produtoRepository.findByNameIgnoreCaseStartingWith(name)
                .stream()
                .filter(produto -> produto.getHabilitar().equals(true))
                .map(assemble::produtoParaDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoDto> buscarPorRangePreco (Double primeiroValor,Double segundoValor){

        if(primeiroValor < 0 || segundoValor < 0){
            throw new ValorInvalidoException();
        }
        if(segundoValor < primeiroValor){
            throw new ValorInvalidoException();
        }

         return produtoRepository.findByPriceBetween(primeiroValor, segundoValor)
                 .stream()
                 .filter(produto -> produto.getHabilitar().equals(true))
                 .map(assemble::produtoParaDto)
                 .collect(Collectors.toList());
    }

    public List<ProdutoDto> buscarPorMarca (String name){
        Marca marca = marcaRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByMarca(marca.getName())
                .stream()
                .filter(produto -> produto.getHabilitar().equals(true))
                .map(assemble::produtoParaDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoDto> buscarPorCategoria (String name){
        Categoria categoria = categoriaRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByCategoria(categoria.getName())
                .stream()
                .filter(produto -> produto.getHabilitar().equals(true))
                .map(assemble::produtoParaDto)
                .collect(Collectors.toList());
    }


    public void excluirProduto (Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto não encontrado"));
        produtoRepository.deleteById(id);
    }



    public void alternarStatus(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        boolean novoStatus = !produto.getHabilitar();

        produtoRepository.atualizarStatusProduto(id, novoStatus);
        log.info("O produto foi {} com êxito: {}", novoStatus ? "habilitado" : "desabilitado", id);
    }




}
