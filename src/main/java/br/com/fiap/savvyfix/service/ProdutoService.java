package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProdutoService implements ServiceDTO<Produto, ProdutoRequest, ProdutoResponse>{

    @Autowired
    ProdutoRepository repo;

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

    public Produto findById(Long id) {
        return  repo.findById(id).orElse(null);
    }
}



