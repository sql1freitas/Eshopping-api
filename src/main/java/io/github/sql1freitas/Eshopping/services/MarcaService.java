package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;


    public Marca save (Marca marca){
        return marcaRepository.save(marca);
    }
}
