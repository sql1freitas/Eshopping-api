package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.MarcaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Marca;
import io.github.sql1freitas.Eshopping.entity.Produto;
import io.github.sql1freitas.Eshopping.exceptions.EntidadeDesabilitadaException;
import io.github.sql1freitas.Eshopping.exceptions.EntidadeHabilitadaException;
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
import java.util.stream.Collectors;

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

    public List<MarcaDto> listarTodas (){
        return marcaRepository.findAll()
                .stream()
                .filter(marca -> marca.getHabilitar().equals(true))
                .map(assemble::marcaParaDto)
                .collect(Collectors.toList());
    }


    public List<ProdutoDto> listarTodosProdutos (Long id) {

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByMarca(marca)
                .stream()
                .filter(produto -> produto.getHabilitar().equals(true))
                .map(assemble::produtoParaDto)
                .collect(Collectors.toList());

    }

    public List<MarcaDto> listarMarcaPorNome (String name){

        return marcaRepository.findByNameIgnoreCaseStartingWith(name)
                .stream()
                .filter(marca -> marca.getHabilitar().equals(true))
                .map(assemble::marcaParaDto)
                .collect(Collectors.toList());

    }


    public void excluirMarca (Long id){

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        marcaRepository.deleteById(id);



    }

    public void desabilitar(Long id){

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
        if(marca.getHabilitar().equals(false)){
            throw new EntidadeDesabilitadaException();
        }

        marcaRepository.desabilitarMarca(id);
        log.info("Marca desabilitada com sucesso: {}", id);
    }

    public void habilitar(Long id){
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
        if(marca.getHabilitar().equals(true)){
            throw new EntidadeHabilitadaException();
        }


        marcaRepository.habilitarMarca(id);
        log.info("Marca habilitada com sucesso: {}", id);
    }



}


