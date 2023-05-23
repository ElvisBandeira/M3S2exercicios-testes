package com.example.exerciciotestes.service;

import antlr.ASTNULLType;
import com.example.exerciciotestes.controller.request.VendaRequest;
import com.example.exerciciotestes.model.Cliente;
import com.example.exerciciotestes.model.Produto;
import com.example.exerciciotestes.model.Venda;
import com.example.exerciciotestes.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class VendaServiceTest {

    @Mock
    ProdutoService produtoService;
    @Mock
    ClienteService clienteService;

    @InjectMocks
    VendaService vendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscaTodosVendas() {
        VendaRepository vendaRepository = (VendaRepository) new ASTNULLType();
        when(vendaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Venda> vendas = vendaService.buscaTodosVendas();
        verify(vendaRepository).findAll();

        assertTrue(vendas.isEmpty());

    }

    @Test
    <object>
    void realizarVenda() {
        Long clienteId = 1L;
        List<Long> produtos = Arrays.asList(1L);
        Double valorVenda = 10.0;
        VendaRequest vendaRequest = new VendaRequest(clienteId, produtos, valorVenda);

        Cliente cliente = new Cliente();
        when(clienteService.buscaClientePorId(clienteId)).thenReturn(cliente);

        Produto produto1 = new Produto();
        when(produtoService.buscaProdutoPorId(1L)).thenReturn(produto1);

        Venda venda = vendaService.realizarVenda(vendaRequest);

        verify(clienteService).buscaClientePorId(clienteId);
        verify(produtoService).buscaProdutoPorId(1L);

        Object vendaRepository = null;
        Mockito.<object>verify((object) vendaRepository).equals(any(Venda.class));

        assertNotNull(venda);


    }

    @Test
    void buscaVendaPorId() {
        Long vendaId = 1L;

        Venda venda = new Venda();
        VendaRepository vendaRepository = Mockito.mock(VendaRepository.class);
        Mockito.when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));

        Venda result = vendaService.buscaVendaPorId(vendaId);

        Mockito.verify(vendaRepository).findById(vendaId);

        assertNotNull(result);

    }

}