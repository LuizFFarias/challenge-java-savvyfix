package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Produto;
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


@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService service;

    @GetMapping
    public Collection<ProdutoResponse> findAll() {
        var entity = service.findAll();
        var response = entity
                .stream().map( service::toResponse ).toList();
        return response;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest produto) {
        var entity = service.toEntity( produto );
        Produto save = service.save( entity );
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath( "/{id}" )
                .buildAndExpand( save.getId() )
                .toUri();
        var response = service.toResponse( save );
        return ResponseEntity.created( uri ).body( response );
    }

    @GetMapping(value = "/{id}")
    public ProdutoResponse findById(@PathVariable Long id) {
        return service.toResponse( service.findById( id ) );
    }




}
