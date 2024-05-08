package br.com.fiap.savvyfix.service;
import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.request.CompraRequest;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.dto.response.CompraResponse;
import br.com.fiap.savvyfix.entity.*;
import br.com.fiap.savvyfix.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompraService implements ServiceDTO<Compra, CompraRequest, CompraResponse>{

    @Autowired
    CompraRepository repo;

    @Autowired
    AtividadesService atividadesService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ClienteService clienteService;

    @Override
    public Compra toEntity(CompraRequest compraRequest) {

        Atividades atividades = null;
        var produto = produtoService.findById(compraRequest.produto().id());
        var cliente = clienteService.findById(compraRequest.cliente().id());

        if (Objects.nonNull(compraRequest.atividades().precoVariado())) {
            List<Atividades> atividades1 = atividadesService.findByPrecoVariado(compraRequest.atividades().precoVariado());

            if (!atividades1.isEmpty()) {
                atividades = atividades1.get(0);
            }
        }
        return Compra.builder()
                .nomeProd( compraRequest.nomeProd() )
                .qntdProd( compraRequest.qntdProd() )
                .valorCompra( compraRequest.valorCompra() )
                .especificacoes( compraRequest.especificacoes())
                .atividades( atividades )
                .produto( produto )
                .cliente( cliente )
                .build();
    }

    @Override
    public CompraResponse toResponse(Compra compra) {
        return CompraResponse.builder()
                .nomeProd( compra.getNomeProd() )
                .qntdProd( compra.getQntdProd() )
                .valorCompra( compra.getValorCompra() )
                .especificacoes( compra.getEspecificacoes())
                .atividades( atividadesService.toResponse(compra.getAtividades()) )
                .produto( produtoService.toResponse(compra.getProduto()))
                .cliente( clienteService.toResponse(compra.getCliente()))
                .build();
    }

    @Override
    public Collection<Compra> findAll() {
        return null;
    }

    @Override
    public Compra save(Compra compra) {
        return null;
    }

    public Compra findById(Long id) {
        return  repo.findById(id).orElse(null);
    }
}



