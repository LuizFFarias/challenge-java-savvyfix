package br.com.fiap.savvyfix.service;
import br.com.fiap.savvyfix.dto.request.AtividadesRequest;
import br.com.fiap.savvyfix.dto.response.AtividadesResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.repository.AtividadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadesService {

    private final AtividadesRepository atividadesRepository;

    @Autowired
    public AtividadesService(AtividadesRepository atividadesRepository) {
        this.atividadesRepository = atividadesRepository;
    }

    public List<AtividadesResponse> getAllAtividades() {
        return atividadesRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AtividadesResponse getAtividadesByPrecoVariado(float precoVariado) {
        return atividadesRepository.findById(precoVariado)
                .map(this::toResponse)
                .orElse(null);
    }

    public AtividadesResponse createAtividades(AtividadesRequest request) {
        Atividades atividades = toEntity(request);
        atividades = atividadesRepository.save(atividades);
        return toResponse(atividades);
    }

    public AtividadesResponse updateAtividades(float precoVariado, AtividadesRequest request) {
        if (!atividadesRepository.existsById(precoVariado)) {
            return null;
        }
        Atividades atividades = toEntity(request);
        atividades.setPrecoVariado(precoVariado);
        atividades = atividadesRepository.save(atividades);
        return toResponse(atividades);
    }

    public void deleteAtividades(float precoVariado) {
        if (atividadesRepository.existsById(precoVariado)) {
            atividadesRepository.deleteById(precoVariado);
        }
    }

    private AtividadesResponse toResponse(Atividades atividades) {
        return new AtividadesResponse(
                atividades.getPrecoVariado(),
                atividades.getHorarioAtual(),
                atividades.getLocalizacaoAtual(),
                atividades.getClimaAtual(),
                atividades.getQntdProcura(),
                atividades.getDemanda(),
                atividades.getCliente()
        );
    }

    private Atividades toEntity(AtividadesRequest request) {
        return new Atividades(
                request.getPrecoVariado(),
                request.getHorarioAtual(),
                request.getLocalizacaoAtual(),
                request.getClimaAtual(),
                request.getQntdProcura(),
                request.getDemanda(),
                request.getCliente()
        );
    }
}
