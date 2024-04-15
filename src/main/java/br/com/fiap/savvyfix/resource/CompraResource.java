package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.request.CompraRequest;
import br.com.fiap.savvyfix.dto.response.AtividadesResponse;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.dto.response.CompraResponse;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Compra;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.CompraRepository;
import br.com.fiap.savvyfix.service.AtividadesService;
import br.com.fiap.savvyfix.service.ClienteService;
import br.com.fiap.savvyfix.service.CompraService;
import br.com.fiap.savvyfix.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compras")
public class CompraResource {

    @Autowired
    CompraService service;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    AtividadesService atividadesService;

    @GetMapping
    public Collection<CompraResponse> findAll() {
        var entity = service.findAll();
        var response = entity
                .stream().map( service::toResponse ).toList();
        return response;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CompraResponse> save(@RequestBody @Valid CompraRequest compra) {
        var entity = service.toEntity( compra );
        var produto = produtoService.findById( compra.produto().id());
        var cliente = clienteService.findById( compra.cliente().id());
        var atividades = atividadesService.findByValor(compra.atividades().precoVariado());

        if (Objects.nonNull( produto )) entity.setProduto(produto);
        if (Objects.nonNull( cliente )) entity.setCliente(cliente);
        if (Objects.nonNull( atividades )) entity.setAtividades((Atividades) atividades);
        Compra save = service.save( entity );
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath( "/{id}" )
                .buildAndExpand( save.getId() )
                .toUri();
        var response = service.toResponse( save );
        return ResponseEntity.created( uri ).body( response );
    }

    @GetMapping(value = "/{id}")
    public CompraResponse findById(@PathVariable Long id) {
        return service.toResponse( service.findById( id ) );
    }


}
