package br.com.fiap.savvyfix.service;


import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse>{

    @Autowired
    private ProdutoRepository repo;

    @Override
    public Produto toEntity(ProdutoRequest produtoRequest) {

        return Produto.builder()
                .nome( produtoRequest.nome() )
                .descricao( produtoRequest.descricao() )
                .marca( produtoRequest.marca() )
                .precoFixo( produtoRequest.precoFixo())
                .build();
    }

    @Override
    public ProdutoResponse toResponse(Produto produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome( produto.getNome() )
                .descricao( produto.getDescricao() )
                .marca( produto.getMarca() )
                .precoFixo( produto.getPrecoFixo())
                .build();
    }

    @Override
    public Collection<Produto> findAll() {
        return repo.findAll();
    }

    @Override
    public Produto save(Produto produto) {
        return repo.save(produto);
    }

    @Override
    public Collection<Produto> findAll(Example<Produto> example) {
        return repo.findAll(example);
    }

    public Produto findById(Long id) {
        return  repo.findById(id).orElse(null);
    }
}



