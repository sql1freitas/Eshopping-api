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
    List<Produto> findByMarca(@Param("nomeMarca") String nomeMarca);
    @Query("SELECT p FROM Produto p WHERE p.categoria.name = :nomeCategoria%")
    List<Produto> findByCategoria(@Param("nomeCategoria") String nomeCategoria);

    List<Produto> findByNameIgnoreCaseStartingWith (String name);
    @Query("SELECT p FROM Produto p WHERE p.price BETWEEN :primeiroValor AND :segundoValor")
    List<Produto> findAllByPriceBetween (Double primeiroValor, Double segundoValor);


}
