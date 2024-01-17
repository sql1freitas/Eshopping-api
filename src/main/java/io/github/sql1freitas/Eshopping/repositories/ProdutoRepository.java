package io.github.sql1freitas.Eshopping.repositories;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository <Produto,Long> {

    @Modifying
    @Query("UPDATE Produto p SET p.habilitar = false WHERE p.id = :id")
    @Transactional
    void desabilitarProduto(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Produto p SET p.habilitar = true WHERE p.id = :id")
    @Transactional
    void habilitarProduto(@Param("id") Long id);


    List<Produto> findByMarca (Marca marca);

    List<Produto> findByCategoria (Categoria categoria);

    List<Produto> findByNameIgnoreCaseStartingWith (String name);
    @Query(value = "select * from produto p where p.price between primeiroValor and segundoValor;")
    List<Produto> findByPriceBetween (Double primeiroValor, Double segundoValor);
}
