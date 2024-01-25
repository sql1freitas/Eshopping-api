package io.github.sql1freitas.Eshopping.services;

import io.github.sql1freitas.Eshopping.dto.Assemble;
import io.github.sql1freitas.Eshopping.dto.CategoriaDto;
import io.github.sql1freitas.Eshopping.dto.ProdutoDto;
import io.github.sql1freitas.Eshopping.entity.Categoria;
import io.github.sql1freitas.Eshopping.repositories.CategoriaRepository;
import io.github.sql1freitas.Eshopping.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final Assemble assemble;


    public CategoriaDto save (Categoria categoria){

        Categoria newCategoria = categoriaRepository.save(categoria);

        return assemble.categoriaDto(newCategoria);
    }

    public Page<CategoriaDto> listarTodos (PageRequest pageRequest){

        return categoriaRepository.findByHabilitar(true, pageRequest)
                .map(assemble::categoriaDto);
    }

    public Page<ProdutoDto> listarTodosProdutos (String name, PageRequest pageRequest){

        Categoria categoria = categoriaRepository.findByHabilitarAndNameIgnoreCaseStartingWith(true, name)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        return produtoRepository.findByHabilitarAndCategoriaNameIgnoreCaseStartingWith(true,name, pageRequest)
                .map(assemble::produtoParaDto);

    }

    public Page<CategoriaDto> listarCategoriaPorNome (String name, PageRequest pageRequest){

        return categoriaRepository.findByHabilitarAndNameIgnoreCaseStartingWith(true, name, pageRequest)
                .map(assemble::categoriaDto);

    }

    public void excluirCategoria (Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoriaRepository.deleteById(id);
    }


    public void alternarStatus(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        boolean novoStatus = !categoria.getHabilitar();

        categoriaRepository.atualizarStatusCategoria(id, novoStatus);
        log.info("A categoria foi {} com êxito: {}", novoStatus ? "habilitado" : "desabilitado", id);
    }
}
