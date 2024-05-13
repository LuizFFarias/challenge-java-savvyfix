package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.CompraRequest;
import br.com.fiap.savvyfix.dto.response.CompraResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Compra;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.service.AtividadesService;
import br.com.fiap.savvyfix.service.ClienteService;
import br.com.fiap.savvyfix.service.CompraService;
import br.com.fiap.savvyfix.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/compras")
public class CompraResource implements ResourceDTO<CompraRequest, CompraResponse>{

    @Autowired
    private CompraService service;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AtividadesService atividadesService;

    @GetMapping
    public ResponseEntity<Collection<CompraResponse>> findAll(
            @RequestParam(name = "nomeProd", required = false) String nomeProd,
            @RequestParam(name = "qntdProd", required = false) Integer qntdProd,
            @RequestParam(name = "valorCompra", required = false) Float valorCompra,
            @RequestParam(name = "especificacoes", required = false) String especificacoes,
            @RequestParam(name = "marca", required = false) String marcaProd,
            @RequestParam(name = "cpfClie", required = false) String cpfClie,
            @RequestParam(name = "nomeClie", required = false) String nomeClie,
            @RequestParam(name = "horario", required = false) LocalTime horario
            ){
    var cliente = Cliente.builder()
            .cpf(cpfClie)
            .nome(nomeClie)
            .build();

    var produto = Produto.builder()
            .marca(marcaProd)
            .build();

    var atividades = Atividades.builder()
            .horarioAtual(horario)
            .build();

    var compra = Compra.builder()
            .nomeProd(nomeProd)
            .qntdProd(qntdProd)
            .valorCompra(valorCompra)
            .especificacoes(especificacoes)
            .atividades(atividades)
            .produto(produto)
            .cliente(cliente)
            .build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withMatcher("nomeProd", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("valorCompra", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("especificacoes", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("marca", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cpf.clie", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("horario", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Compra> example = Example.of(compra, matcher);

        Collection<Compra> all = service.findAll(example);
        if (Objects.isNull(all) || all.isEmpty()) return ResponseEntity.notFound().build();
        var response = all.stream().map(service::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<CompraResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }


    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<CompraResponse> save(@RequestBody @Valid CompraRequest compra) {
        var entity = service.toEntity( compra );
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
