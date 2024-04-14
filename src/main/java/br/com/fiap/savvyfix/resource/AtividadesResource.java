package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.AtividadesRequest;
import br.com.fiap.savvyfix.dto.response.AtividadesResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.repository.AtividadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atividades")
public class AtividadesResource {

    private final AtividadesRepository atividadesRepository;

    @Autowired
    public AtividadesResource(AtividadesRepository atividadesRepository) {
        this.atividadesRepository = atividadesRepository;
    }

    @GetMapping
    public ResponseEntity<List<AtividadesResponse>> getAllAtividades() {
        List<AtividadesResponse> atividadesResponses = atividadesRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(atividadesResponses);
    }

    @GetMapping("/{precoVariado}")
    public ResponseEntity<AtividadesResponse> getAtividadesByPrecoVariado(@PathVariable float precoVariado) {
        return atividadesRepository.findById(precoVariado)
                .map(atividades -> ResponseEntity.ok(toResponse(atividades)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AtividadesResponse> createAtividades(@Valid @RequestBody AtividadesRequest request) {
        Atividades atividades = toEntity(request);
        atividades = atividadesRepository.save(atividades);
        return ResponseEntity.ok(toResponse(atividades));
    }

    @PutMapping("/{precoVariado}")
    public ResponseEntity<AtividadesResponse> updateAtividades(@PathVariable float precoVariado, @Valid @RequestBody AtividadesRequest request) {
        if (!atividadesRepository.existsById(precoVariado)) {
            return ResponseEntity.notFound().build();
        }
        Atividades atividades = toEntity(request);
        atividades.setPrecoVariado(precoVariado);
        atividades = atividadesRepository.save(atividades);
        return ResponseEntity.ok(toResponse(atividades));
    }

    @DeleteMapping("/{precoVariado}")
    public ResponseEntity<Void> deleteAtividades(@PathVariable float precoVariado) {
        if (!atividadesRepository.existsById(precoVariado)) {
            return ResponseEntity.notFound().build();
        }
        atividadesRepository.deleteById(precoVariado);
        return ResponseEntity.noContent().build();
    }

    private AtividadesResponse toResponse(Atividades atividades) {
        return AtividadesResponse.builder()
                .precoVariado(atividades.getPrecoVariado())
                .horarioAtual(atividades.getHorarioAtual())
                .localizacaoAtual(atividades.getLocalizacaoAtual())
                .climaAtual(atividades.getClimaAtual())
                .qntdProcura(atividades.getQntdProcura())
                .demanda(atividades.getDemanda())
                .cliente(atividades.getCliente())
                .build();
    }

    private Atividades toEntity(AtividadesRequest request) {
        return Atividades.builder()
                .precoVariado(request.getPrecoVariado())
                .horarioAtual(request.getHorarioAtual())
                .localizacaoAtual(request.getLocalizacaoAtual())
                .climaAtual(request.getClimaAtual())
                .qntdProcura(request.getQntdProcura())
                .demanda(request.getDemanda())
                .cliente(request.getCliente())
                .build();
    }
}



