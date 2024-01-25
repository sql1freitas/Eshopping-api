package io.github.sql1freitas.Eshopping.repositories;

import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.entity.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository <Categoria, Long> {

    @Modifying
    @Query("UPDATE Categoria c SET c.habilitar = :status WHERE c.id = :id")
    @Transactional
    void atualizarStatusCategoria(@Param("id") Long id, @Param("status") Boolean status);

    Optional<Categoria> findByHabilitarAndNameIgnoreCaseStartingWith (Boolean habilitar, String name);

    Page<Categoria> findByHabilitarAndNameIgnoreCaseStartingWith(Boolean habilitar, String name, Pageable pageable);

    Page<Categoria> findByHabilitar(Boolean habilitar, Pageable pageable);
}
