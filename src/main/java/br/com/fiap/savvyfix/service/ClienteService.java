package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponse> getAllClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public ClienteResponse createCliente(ClienteRequest request) {
        Cliente cliente = toEntity(request);
        cliente = clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    public ClienteResponse updateCliente(Long id, ClienteRequest request) {
        if (!clienteRepository.existsById(id)) {
            return null;
        }
        Cliente cliente = toEntity(request);
        cliente.setId(id);
        cliente = clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    public void deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getSenha(),
                cliente.getEndereco()
        );
    }

    private Cliente toEntity(ClienteRequest request) {
        return new Cliente(
                null, // O ID será gerado automaticamente pelo banco de dados
                request.getNome(),
                request.getCpf(),
                request.getSenha(),
                request.getEndereco()
        );
    }
}
