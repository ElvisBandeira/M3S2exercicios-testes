package com.example.exerciciotestes.service;

import com.example.exerciciotestes.controller.request.VendaRequest;
import com.example.exerciciotestes.model.Venda;
import com.example.exerciciotestes.repository.VendaRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public VendaService(VendaRepository VendaRepository, ClienteService clienteService, ProdutoService produtoService) {
        this.vendaRepository = VendaRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }


    public List<Venda> buscaTodosVendas(){
        return this.vendaRepository.findAll();
    }

    public Venda realizarVenda(@Valid VendaRequest vendaRequest){
        clienteService.buscaClientePorId(vendaRequest.getClienteId());
        for(Long idProduto: vendaRequest.getProdutos()){
            produtoService.buscaProdutoPorId(idProduto);
        }
        vendaRepository.save(new Venda());

        // TODO: código a ser feito no método TDD
        return null;
    }

    public Venda buscaVendaPorId (Long id){
        return this.vendaRepository.findById(id).orElse(null);
    }

    public Venda findAll() {
        return this.findAll();
    }
}

