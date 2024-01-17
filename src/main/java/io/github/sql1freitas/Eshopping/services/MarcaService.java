package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;



    public Marca save (Marca marca){
        return marcaRepository.save(marca);
    }


    public List<Produto> listarTodos (Long id) {

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return marca.getProdutos();
    }
}


