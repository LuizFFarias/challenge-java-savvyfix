package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.ClienteRepository;
import br.com.fiap.savvyfix.service.ClienteService;
import br.com.fiap.savvyfix.service.EnderecoService;
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
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public Collection<ClienteResponse> findAll() {
        var entity = service.findAll();
        var response = entity
                .stream().map( service::toResponse ).toList();
        return response;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody @Valid ClienteRequest cliente) {
        var entity = service.toEntity( cliente );
        var endereco = enderecoService.findByCep( cliente.endereco().cep() );
        if (Objects.nonNull( endereco )) entity.setEndereco((Endereco) endereco);
        Cliente save = service.save( entity );
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .replacePath( "/{id}" )
                .buildAndExpand( save.getId() )
                .toUri();
        var response = service.toResponse( save );
        return ResponseEntity.created( uri ).body( response );
    }

    @GetMapping(value = "/{id}")
    public ClienteResponse findById(@PathVariable Long id) {
        return service.toResponse( service.findById( id ) );
    }


}
