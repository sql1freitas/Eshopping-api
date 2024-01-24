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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<ProdutoDto> listarTodos (PageRequest pageRequest){

       return produtoRepository.findByHabilitar(true, pageRequest)
               .map(assemble::produtoParaDto);
    }

    public Page<ProdutoDto> buscarPorNome(String name, PageRequest pageRequest){

        return produtoRepository.findByHabilitarAndNameIgnoreCaseStartingWith(true, name, pageRequest)
                .map(assemble::produtoParaDto);
    }

    public Page<ProdutoDto> buscarPorRangePreco (Double primeiroValor, Double segundoValor, PageRequest pageRequest){

        if(primeiroValor < 0 || segundoValor < 0){
            throw new ValorInvalidoException();
        }
        if(segundoValor < primeiroValor){
            throw new ValorInvalidoException();
        }

         return produtoRepository.findByHabilitarAndPriceBetween(true, primeiroValor, segundoValor, pageRequest)
                 .map(assemble::produtoParaDto);
    }

    public Page<ProdutoDto> buscarPorMarca (String name, PageRequest pageRequest){
        Marca marca = marcaRepository.findByNameIgnoreCaseStartingWith(name)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByHabilitarAndMarca(name,true,pageRequest)
                .map(assemble::produtoParaDto);

    }

    public Page<ProdutoDto> buscarPorCategoria (String name, PageRequest pageRequest){
        Categoria categoria = categoriaRepository.findByNameIgnoreCaseStartingWith(name)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        return produtoRepository.findByHablitarAndCategoria(name, true, pageRequest)
                .map(assemble::produtoParaDto);
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
