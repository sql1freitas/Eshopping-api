package io.github.sql1freitas.Eshopping.repositories;

import io.github.sql1freitas.Eshopping.entity.Marca;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.anyOf;

public interface MarcaRepository extends JpaRepository <Marca, Long> {


    @Modifying
    @Query("UPDATE Marca m SET m.habilitar = :status WHERE m.id = :id")
    @Transactional
    void atualizarStatusMarca(@Param("id") Long id, @Param("status") Boolean status);

   Optional< Marca > findByNameIgnoreCase(String name);
   List<Marca> findByNameIgnoreCaseStartingWith(String name);



}

