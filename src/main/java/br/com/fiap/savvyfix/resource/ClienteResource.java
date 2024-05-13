package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.service.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteResource implements ResourceDTO<ClienteRequest, ClienteResponse>{

    @Autowired
    private ClienteService service;

    @Autowired
    private EnderecoService enderecoService;

   @GetMapping
   public ResponseEntity<Collection<ClienteResponse>> findAll(
           @RequestParam(name = "cpf", required = false ) String cpf,
           @RequestParam(name = "nome", required = false) String nome,
           @RequestParam(name = "endereco.id", required = false) Long idEndereco,
           @RequestParam(name = "endereco.cep", required = false) String cep,
           @RequestParam(name = "endereco.rua", required = false) String rua,
           @RequestParam(name = "endereco.numero", required = false) String numero,
           @RequestParam(name = "endereco.bairro", required = false) String bairro
   ){
       Endereco endereco = null;
       if (Objects.nonNull(idEndereco) && idEndereco > 0){
           Endereco enderecos = enderecoService.findById(idEndereco);
           endereco = Endereco.builder()
                   .id(enderecos.getId())
                   .cep(enderecos.getCep())
                   .rua(enderecos.getRua())
                   .numero(enderecos.getNumero())
                   .bairro(enderecos.getBairro())
                   .build();
       } else {
           endereco = Endereco.builder()
                   .id(idEndereco)
                   .cep(cep)
                   .rua(rua)
                   .numero(numero)
                   .bairro(bairro)
                   .build();
       }

        var cliente = Cliente.builder()
                .cpf(cpf)
                .nome(nome)
                .endereco(endereco)
                .build();

       ExampleMatcher matcher = ExampleMatcher
               .matching()
               .withMatcher("cpf", ExampleMatcher.GenericPropertyMatchers.contains())
               .withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains())
               .withMatcher("endereco.cep", ExampleMatcher.GenericPropertyMatchers.contains())
               .withMatcher("endereco.bairro", ExampleMatcher.GenericPropertyMatchers.contains())
               .withIgnoreCase()
               .withIgnoreNullValues();

       Example<Cliente> example = Example.of(cliente, matcher);

       Collection<Cliente> all = service.findAll(example);
       if (Objects.isNull(all) || all.isEmpty()) return ResponseEntity.notFound().build();
       var response = all.stream().map(service::toResponse).toList();
       return ResponseEntity.ok(response);
   };

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable  Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<ClienteResponse> save(@RequestBody @Valid ClienteRequest cliente) {
        var entity = service.toEntity( cliente );
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
