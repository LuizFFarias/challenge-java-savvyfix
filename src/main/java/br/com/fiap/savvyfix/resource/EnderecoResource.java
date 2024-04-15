package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.EnderecoRequest;
import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.EnderecoResponse;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.repository.EnderecoRepository;
import br.com.fiap.savvyfix.service.EnderecoService;
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
@RequestMapping("/enderecos")
public class EnderecoResource {


    @Autowired
    EnderecoService service;

    @GetMapping
    public Collection<EnderecoResponse> findAll() {
        var entity = service.findAll();
        var response = entity
                .stream().map( service::toResponse ).toList();
        return response;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<EnderecoResponse> save(@RequestBody @Valid EnderecoRequest endereco) {
        var entity = service.toEntity( endereco );
        Endereco save = service.save( entity );
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath( "/{cep}" )
                .buildAndExpand( save.getCep() )
                .toUri();
        var response = service.toResponse( save );
        return ResponseEntity.created( uri ).body( response );
    }

    @GetMapping(value = "/cep/{cep}")
    public ResponseEntity<List<EnderecoResponse>> findByCep(@PathVariable String cep) {
        var entity = service.findByCep( cep );
        if (Objects.isNull( entity )) return ResponseEntity.notFound().build();
        var response = entity.stream().map( service::toResponse ).toList();
        return ResponseEntity.ok( response );
    }




}
