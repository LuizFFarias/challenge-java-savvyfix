package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.AtividadesRequest;
import br.com.fiap.savvyfix.dto.response.AtividadesResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.service.AtividadesService;
import br.com.fiap.savvyfix.service.ClienteService;
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
@RequestMapping("/atividades")
public class AtividadesResource implements ResourceDTO<AtividadesRequest, AtividadesResponse>{

    @Autowired
    private AtividadesService service;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Collection<AtividadesResponse>> findAll(
            @RequestParam(name = "horario", required = false) LocalTime horario,
            @RequestParam(name = "localizacao", required = false) String localizacao,
            @RequestParam(name = "demanda", required = false) String demanda,
            @RequestParam(name = "qntdProcura", required = false) Integer procura,
            @RequestParam(name = "clima", required = false) String clima,
            @RequestParam(name = "precoVariado", required = false) Float precoVariado,
            @RequestParam(name = "cliente.cpf", required = false) String cpfCliente
            ){
        var cliente = Cliente.builder()
                .cpf(cpfCliente)
                .build();


        var atividades = Atividades.builder()
                .horarioAtual(horario)
                .localizacaoAtual(localizacao)
                .demanda(demanda)
                .qntdProcura(procura)
                .climaAtual(clima)
                .precoVariado(precoVariado)
                .cliente(cliente)
                .build();

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withMatcher("localizacao", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("horario", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("demanda", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("qntdProcura", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("clima", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("precoVariado", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("cliente.cpf", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Atividades> example = Example.of(atividades, matcher);

        Collection<Atividades> all = service.findAll(example);
        if (Objects.isNull(all) || all.isEmpty()) return ResponseEntity.notFound().build();
        var response = all.stream().map(service::toResponse).toList();
        return ResponseEntity.ok(response);
    };


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<AtividadesResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<AtividadesResponse> save(@RequestBody @Valid AtividadesRequest atividades) {
        var entity = service.toEntity( atividades );
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



