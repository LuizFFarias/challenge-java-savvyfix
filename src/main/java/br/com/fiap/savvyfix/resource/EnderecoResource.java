package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.EnderecoRequest;
import br.com.fiap.savvyfix.dto.response.EnderecoResponse;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enderecos")
public class EnderecoResource {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoResource(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponse>> getAllEnderecos() {
        List<EnderecoResponse> enderecoResponses = enderecoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enderecoResponses);
    }

    @GetMapping("/{cep}")
    public ResponseEntity<EnderecoResponse> getEnderecoByCep(@PathVariable String cep) {
        return enderecoRepository.findByCep(cep)
                .stream()
                .map(endereco -> ResponseEntity.ok(toResponse(endereco)))
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EnderecoResponse> createEndereco(@Valid @RequestBody EnderecoRequest request) {
        Endereco endereco = toEntity(request);
        endereco = enderecoRepository.save(endereco);
        return ResponseEntity.ok(toResponse(endereco));
    }

    private EnderecoResponse toResponse(Endereco endereco) {
        return EnderecoResponse.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .pais(endereco.getPais())
                .build();
    }

    private Endereco toEntity(EnderecoRequest request) {
        return Endereco.builder()
                .cep(request.getCep())
                .rua(request.getRua())
                .numero(request.getNumero())
                .bairro(request.getBairro())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .pais(request.getPais())
                .build();
    }
}
