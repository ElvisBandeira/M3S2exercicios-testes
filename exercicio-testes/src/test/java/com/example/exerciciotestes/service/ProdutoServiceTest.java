package com.example.exerciciotestes.service;

import com.example.exerciciotestes.controller.request.ProdutoRequest;
import com.example.exerciciotestes.model.Produto;
import com.example.exerciciotestes.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void buscaTodosProdutos() {

        List<Produto> produtosVop = new ArrayList<>();
        produtosVop.add(new Produto(1L, "Produto 1", 10.0));
        when(produtoRepository.findAll()).thenReturn(produtosVop);

        List<Produto> produtos = produtoService.buscaTodosProdutos();

        assertEquals(1, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNomeProduto());
        verify(produtoRepository).findAll();

    }

    @Test
    void buscaProdutoPorId() {

        Produto produtoVop = new Produto(1L, "Produto 1", 10.0);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoVop));

        Produto produto = produtoService.buscaProdutoPorId(1L);

        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNomeProduto());
        verify(produtoRepository).findById(1L);

    }

    @Test
    void salvaProduto() {

        ProdutoRequest produtoRequest = new ProdutoRequest("Produto 1", 10.0);
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto produtoSalvo = produtoService.salvaProduto(produtoRequest);

        assertNotNull(produtoSalvo);
        assertEquals("Produto 1", produtoSalvo.getNomeProduto());
        assertEquals(10.0, produtoSalvo.getValorProduto());
        verify(produtoRepository).save(any(Produto.class));

    }

    @Test
    void atualizarProduto() {

        Long produtoId = 1L;
        ProdutoRequest produtoRequest = new ProdutoRequest("Produto com Atualização", 10.0);
        Produto produtoAtualVop = new Produto(produtoId, "Produto Anterior", 10.0);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoAtualVop));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Produto produtoAtualizado = produtoService.atualizarProduto(produtoId, produtoRequest);

        assertNotNull(produtoAtualizado);
        assertEquals(produtoId, produtoAtualizado.getId());
        assertEquals("Produto com Atualização", produtoAtualizado.getNomeProduto());
        assertEquals(10.0, produtoAtualizado.getValorProduto());
        verify(produtoRepository).findById(produtoId);
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void detelaProdutoPorId() {

        Long produtoId = 1L;

        produtoService.detelaProdutoPorId(produtoId);

        verify(produtoRepository).deleteById(produtoId);
    }
}