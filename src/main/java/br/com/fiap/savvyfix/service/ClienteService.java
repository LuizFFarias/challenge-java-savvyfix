package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClienteService implements  ServiceDTO<Cliente, ClienteRequest, ClienteResponse>{

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoService enderecoService;
    @Override
    public Cliente toEntity(ClienteRequest clienteRequest) {

        Endereco endereco = null;

        if (Objects.nonNull(clienteRequest.endereco().cep())) {
            List<Endereco> enderecos = enderecoService.findByCep(clienteRequest.endereco().cep());

            if (!enderecos.isEmpty()) {
                endereco = enderecos.get(0);
            }
        }
        return Cliente.builder()
                .nome( clienteRequest.nome() )
                .cpf( clienteRequest.cpf() )
                .senha( clienteRequest.senha() )
                .endereco( endereco )
                .build();
    }

    @Override
    public ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .nome( cliente.getNome() )
                .cpf( cliente.getCpf() )
                .senha( cliente.getSenha() )
                .endereco( enderecoService.toResponse(cliente.getEndereco()) )
                .build();
    }

    @Override
    public Collection<Cliente> findAll() {
        return repo.findAll();
    }

    @Override
    public Cliente save(Cliente cliente) {
        return repo.save( cliente );
    }

    public Cliente findById(Long id) {
        return  repo.findById(id).orElse(null);
    }
}
