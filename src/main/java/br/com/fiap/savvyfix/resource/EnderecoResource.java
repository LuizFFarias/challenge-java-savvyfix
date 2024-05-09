package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.EnderecoRequest;
import br.com.fiap.savvyfix.dto.response.EnderecoResponse;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/enderecos")
public class EnderecoResource implements ResourceDTO<EnderecoRequest, EnderecoResponse>{


    @Autowired
    EnderecoService service;

    @GetMapping
    public ResponseEntity<Collection<EnderecoResponse>> findAll(
            @RequestParam(name = "cep", required = false) String cep,
            @RequestParam(name = "rua", required = false) String rua,
            @RequestParam(name = "numero", required = false) String numero,
            @RequestParam(name = "bairro", required = false) String bairro,
            @RequestParam(name = "cidade", required = false) String cidade,
            @RequestParam(name = "estado", required = false) String estado,
            @RequestParam(name = "pais", required = false) String pais
    ){
        var endereco = Endereco.builder()
                .cep(cep)
                .rua(rua)
                .numero(numero)
                .bairro(bairro)
                .cidade(cidade)
                .estado(estado)
                .pais(pais)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Endereco> example = Example.of(endereco, matcher);

        Collection<Endereco> all = service.findAll(example);
        if (Objects.isNull(all) || all.isEmpty()) return ResponseEntity.notFound().build();
        var response = all.stream().map(service::toResponse).toList();
        return ResponseEntity.ok(response);
    }



    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<EnderecoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Transactional
    @PostMapping
    public ResponseEntity<EnderecoResponse> save(@RequestBody @Valid EnderecoRequest endereco) {
        var entity = service.toEntity( endereco );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created( uri ).body(resposta);
    }




}
