package io.github.sql1freitas.Eshopping.repositories;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoriaRepository extends JpaRepository <Categoria, Long> {

    @Modifying
    @Query("UPDATE Categoria c SET c.habilitar = false WHERE c.id = :id")
    @Transactional
    void desabilitarCategoria(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Categoria c SET c.habilitar = true WHERE c.id = :id")
    @Transactional
    void habilitarCategoria(@Param("id") Long id);
}
