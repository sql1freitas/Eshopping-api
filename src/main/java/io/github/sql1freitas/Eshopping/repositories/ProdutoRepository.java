package io.github.sql1freitas.Eshopping.repositories;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository <Produto,Long> {

    @Modifying
    @Query("UPDATE Produto p SET p.habilitar = :status WHERE p.id = :id")
    @Transactional
    void atualizarStatusProduto(@Param("id") Long id, @Param("status") Boolean status);

    @Query("SELECT p FROM Produto p WHERE p.marca.name = :nomeMarca%")
    Page<Produto> findByHabilitarAndMarca(@Param("nomeMarca") String nomeMarca, Boolean habilitar, Pageable pageable);
    @Query("SELECT p FROM Produto p WHERE p.categoria.name = :nomeCategoria%")
    Page<Produto> findByHablitarAndCategoria(@Param("nomeCategoria") String nomeCategoria, Boolean habilitar, Pageable pageable);

    Page<Produto> findByHabilitarAndNameIgnoreCaseStartingWith (Boolean habilitar, String name, Pageable pageable);
    @Query("SELECT p FROM Produto p WHERE p.price BETWEEN :primeiroValor AND :segundoValor")
    Page<Produto> findByHabilitarAndPriceBetween (Boolean habilitar, Double primeiroValor, Double segundoValor, Pageable pageable);

    Page<Produto> findByHabilitar(boolean habilitar, Pageable pageable);

}
