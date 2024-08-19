package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/produtos", produces = "application/json")
@Tag(name = "savvyfix-api")
public class ProdutoResource implements ResourceDTO<ProdutoRequest, ProdutoResponse>{

    @Autowired
    private ProdutoService service;


    @Operation(summary = "Realiza busca de todos os produtos registrados e pelo nome, marca e preço", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar dados")
    })
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
    @Operation(summary = "Realiza busca dos dados dos produtos pelo id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar dados")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }



    @Override
    @Operation(summary = "Realiza o cadastro de novos produtos para venda", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para serem cadastrados"),
            @ApiResponse(responseCode = "500", description = "Erro ao cadastrar")
    })
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
