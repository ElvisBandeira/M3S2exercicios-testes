package com.example.exerciciotestes.service;

import com.example.exerciciotestes.controller.request.ClienteRequest;
import com.example.exerciciotestes.model.Cliente;
import com.example.exerciciotestes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscaTodosClientes() {
       List<Cliente> clientesVop = new ArrayList<>();
       clientesVop.add(new Cliente(1L, "Cliente 1", 10.0));
       when(clienteRepository.findAll()).thenReturn(clientesVop);

       List<Cliente> clientes = clienteService.buscaTodosClientes();

       assertEquals(1, clientes.size());
       assertEquals("Cliente 1", clientes.get(0).getNomeCliente());
       verify(clienteRepository).findAll();

    }

    @Test
    void buscaClientePorId() {

        Long id = 1L;
        Cliente cliente = new Cliente(id, "Cliente 1", 10.0);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente clienteResultado = clienteService.buscaClientePorId(id);

        assertNotNull(clienteResultado);
        assertEquals(cliente.getNomeCliente(), clienteResultado.getNomeCliente());
        verify(clienteRepository).findById(id);
    }

    @Test
    void salvarCliente() {

        ClienteRequest clienteRequest = new ClienteRequest("Cliente 1", 10.0);

        Mockito.doAnswer(invocation -> { Cliente cliente = invocation.getArgument(0);
            return cliente;
        }).when(clienteRepository).save(Mockito.any(Cliente.class));

        Cliente clienteSalvo = clienteService.salvarCliente(clienteRequest);

        assertNotNull(clienteSalvo);
        assertEquals("Cliente 1", clienteSalvo.getNomeCliente());
        assertEquals(10.0, clienteSalvo.getSaldoCliente());
        verify(clienteRepository).save(ArgumentMatchers.any(Cliente.class));

    }

    @Test
    void atualizarCliente() {
        Long clienteId = 1L;
        ClienteRequest clienteRequest = new ClienteRequest("Cliente com Atualização", 10.0);
        Cliente clienteAtualVop = new Cliente(clienteId, "Cliente Anterior", 10.0);

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteAtualVop));
        when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente clienteAtualizado = clienteService.atualizarCliente(clienteId, clienteRequest);

        assertNotNull(clienteAtualizado);
        assertEquals(clienteId, clienteAtualizado.getId());
        assertEquals("Cliente com Atualização", clienteAtualizado.getNomeCliente());
        assertEquals(10.0, clienteAtualizado.getSaldoCliente());
        verify(clienteRepository).findById(clienteId);
        verify(clienteRepository).save(ArgumentMatchers.any(Cliente.class));
    }

    @Test
    void delelaClientePorId() {

        Long clienteId = 1L;
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(HttpClientErrorException.class, () -> clienteService.deletaClientePorId(clienteId));
        verify(clienteRepository).findById(clienteId);
        verify(clienteRepository, never()).deleteById(clienteId);
    }
}