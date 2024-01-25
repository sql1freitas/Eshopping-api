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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<MarcaDto> listarTodas (PageRequest pageRequest){

        return marcaRepository.findByHabilitar(true, pageRequest)
                .map(assemble::marcaParaDto);
    }


    public Page<ProdutoDto> listarTodosProdutos (String name, PageRequest pageRequest) {

        Marca marca = marcaRepository.findByHabilitarAndNameIgnoreCaseStartingWith(true, name)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        return produtoRepository.findByHabilitarAndMarca(name, true, pageRequest)
                .map(assemble::produtoParaDto);

    }

    public Page<MarcaDto> listarMarcaPorNome (String name, PageRequest pageRequest){

        return marcaRepository.findByHabilitarAndNameIgnoreCaseStartingWith(name, true, pageRequest)
                .map(assemble::marcaParaDto);


    }


    public void excluirMarca (Long id){

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));

        marcaRepository.deleteById(id);



    }

    public void alternarStatus(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        boolean novoStatus = !marca.getHabilitar();

        marcaRepository.atualizarStatusMarca(id, novoStatus);
        log.info("A marca foi {} com êxito: {}", novoStatus ? "habilitado" : "desabilitado", id);
    }



}


