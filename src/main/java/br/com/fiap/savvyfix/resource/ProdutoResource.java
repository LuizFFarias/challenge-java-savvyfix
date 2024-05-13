package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/produtos")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse>{

    @Autowired
    ProdutoService service;


    @GetMapping
    public ResponseEntity<Collection<ProdutoResponse>> findAll(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao,
            @RequestParam(name = "marca", required = false) String marca,
            @RequestParam(name = "preco", required = false) Float preco
    ) {
        var produto = Produto.builder()
                .nome(nome)
                .descricao(descricao)
                .marca(marca)
                .precoFixo(preco)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("marca", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("preco", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Produto> example = Example.of(produto, matcher);

        Collection<Produto> all = service.findAll(example);
        if (Objects.isNull(all) || all.isEmpty()) return ResponseEntity.notFound().build();
        var response = all.stream().map(service::toResponse).toList();
        return ResponseEntity.ok(response);

    }
    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }



    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<ProdutoResponse> save(@RequestBody @Valid ProdutoRequest produto) {
       var entity = service.toEntity( produto );
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
