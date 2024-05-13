package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.CompraRequest;
import br.com.fiap.savvyfix.dto.response.CompraResponse;
import br.com.fiap.savvyfix.entity.Compra;
import br.com.fiap.savvyfix.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class CompraService implements ServiceDTO<Compra, CompraRequest, CompraResponse>{

    @Autowired
    private CompraRepository repo;

    @Autowired
    private AtividadesService atividadesService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Override
    public Compra toEntity(CompraRequest compraRequest) {


        var produto = produtoService.findById(compraRequest.produto().id());
        var cliente = clienteService.findById(compraRequest.cliente().id());
        var atividades = atividadesService.findById(compraRequest.atividades().id());

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
        var atividades = atividadesService.toResponse(compra.getAtividades());
        var produto = produtoService.toResponse(compra.getProduto());
        var cliente = clienteService.toResponse(compra.getCliente());

        if (Objects.nonNull(atividades) && atividades.precoVariado() > 0){
            var precoVariado = atividades.precoVariado();
            var qntdProd = compra.getQntdProd();
            var valorCompra = precoVariado * qntdProd;

            return CompraResponse.builder()
                    .id(compra.getId())
                    .nomeProd( compra.getNomeProd() )
                    .qntdProd( compra.getQntdProd() )
                    .valorCompra(Float.valueOf(valorCompra))
                    .especificacoes( compra.getEspecificacoes())
                    .atividades( atividades )
                    .produto( produto )
                    .cliente( cliente )
                    .build();
        } else {
            return CompraResponse.builder()
                    .id(compra.getId())
                    .nomeProd( compra.getNomeProd() )
                    .qntdProd( compra.getQntdProd() )
                    .valorCompra( compra.getValorCompra() )
                    .especificacoes( compra.getEspecificacoes())
                    .atividades( atividades )
                    .produto( produto )
                    .cliente( cliente )
                    .build();
        }

    }

    @Override
    public Collection<Compra> findAll() {
        return repo.findAll();
    }

    @Override
    public Compra save(Compra compra) {
        return repo.save(compra);
    }

    @Override
    public Collection<Compra> findAll(Example<Compra> example) {
        return repo.findAll(example);
    }

    public Compra findById(Long id) {
        return  repo.findById(id).orElse(null);
    }
}



