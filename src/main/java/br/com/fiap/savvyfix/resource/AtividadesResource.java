package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.AtividadesRequest;
import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.response.AtividadesResponse;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.dto.response.EnderecoResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.AtividadesRepository;
import br.com.fiap.savvyfix.service.AtividadesService;
import br.com.fiap.savvyfix.service.ClienteService;
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
@RequestMapping("/atividades")
public class AtividadesResource {

    @Autowired
    AtividadesService service;

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public Collection<AtividadesResponse> findAll() {
        var entity = service.findAll();
        var response = entity
                .stream().map( service::toResponse ).toList();
        return response;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<AtividadesResponse> save(@RequestBody @Valid AtividadesRequest atividades) {
        var entity = service.toEntity( atividades );
        var cliente = clienteService.findById( atividades.cliente().id() );
        if (Objects.nonNull( cliente )) entity.setCliente(cliente);
        Atividades save = service.save( entity );
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath( "/{precoVariado}" )
                .buildAndExpand( save.getPrecoVariado() )
                .toUri();
        var response = service.toResponse( save );
        return ResponseEntity.created( uri ).body( response );
    }

    @GetMapping(value = "/cep/{cep}")
    public ResponseEntity<List<AtividadesResponse>> findByValor(@PathVariable float valorVariado) {
        var entity = service.findByValor( valorVariado );
        if (Objects.isNull( entity )) return ResponseEntity.notFound().build();
        var response = entity.stream().map( service::toResponse ).toList();
        return ResponseEntity.ok( response );
    }


}



