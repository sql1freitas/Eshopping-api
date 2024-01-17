package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.MarcaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.repositories.MarcaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MarcaService {

    private final MarcaRepository marcaRepository;
    private final ProdutoRepository produtoRepository;
    private final Assemble assemble;



    public MarcaDto save (Marca marca){
        Marca newMarca = marcaRepository.save(marca);

        return assemble.marcaParaDto(newMarca);
    }

    public List<Marca> listarTodas (){
        return marcaRepository.findAll();
    }


    public List<Produto> listarTodosProdutos (Long id) {

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByMarca(marca);

    }

    public List<Marca> listarMarcaPorNome (String name){

        return marcaRepository.findByNameIgnoreCaseStartingWith(name);

    }


    public void excluirMarca (Long id){

        marcaRepository.deleteById(id);



    }

    public void desabilitar(Long id){
        marcaRepository.desabilitarMarca(id);
        log.info("Marca desabilitada com sucesso: {}", id);
    }

    public void habilitar(Long id){
        marcaRepository.habilitarMarca(id);
        log.info("Marca habilitada com sucesso: {}", id);
    }



}


