package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.EnderecoRequest;
import br.com.fiap.savvyfix.dto.response.EnderecoResponse;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<EnderecoResponse> getAllEnderecos() {
        return enderecoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EnderecoResponse getEnderecoByCep(String cep) {
        return enderecoRepository.findByCep(cep)
                .map(this::toResponse)
                .orElse(null);
    }

    public EnderecoResponse createEndereco(EnderecoRequest request) {
        Endereco endereco = toEntity(request);
        endereco = enderecoRepository.save(endereco);
        return toResponse(endereco);
    }

    public EnderecoResponse updateEndereco(String cep, EnderecoRequest request) {
        if (!enderecoRepository.existsById(cep)) {
            return null;
        }
        Endereco endereco = toEntity(request);
        endereco.setCep(cep);
        endereco = enderecoRepository.save(endereco);
        return toResponse(endereco);
    }

    public void deleteEndereco(String cep) {
        if (enderecoRepository.existsById(cep)) {
            enderecoRepository.deleteById(cep);
        }
    }

    private EnderecoResponse toResponse(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getCep(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPais()
        );
    }

    private Endereco toEntity(EnderecoRequest request) {
        return new Endereco(
                request.getCep(),
                request.getRua(),
                request.getNumero(),
                request.getBairro(),
                request.getCidade(),
                request.getEstado(),
                request.getPais()
        );
    }
}
