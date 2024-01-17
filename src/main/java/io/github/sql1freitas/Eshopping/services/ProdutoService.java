package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.repositories.CategoriaRepository;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;


    @Transactional
    public Produto save(Produto produto, Long idCategoria, Long idMarca){

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


        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos (){
        return produtoRepository.findAll();
    }


    public List<Produto> buscarPorNome(String name){
        return produtoRepository.findByNameIgnoreCaseStartingWith(name);
    }

    public List<Produto> buscarPorRangePreco (Double primeiroValor,Double segundoValor){

         return produtoRepository.findByPriceBetween(primeiroValor, segundoValor);
    }

    public List<Produto> buscarPorMarca (Long id){
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByMarca(marca);
    }

    public List<Produto> buscarPorCategoria (Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByCategoria(categoria);
    }


    public void excluirProduto (Long id){
        produtoRepository.deleteById(id);
    }

    public void desabilitar(Long id){
        produtoRepository.desabilitarProduto(id);
    }

    public void habilitar(Long id){
        produtoRepository.habilitarProduto(id);
    }




}
