package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.ClienteRequest;
import br.com.fiap.savvyfix.dto.request.EnderecoRequest;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.dto.response.EnderecoResponse;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EnderecoService implements ServiceDTO<Endereco, EnderecoRequest, EnderecoResponse>{

    @Autowired
    EnderecoRepository repo;

    @Override
    public Endereco toEntity( EnderecoRequest enderecoRequest) {

        return Endereco.builder()
                .cep( enderecoRequest.cep() )
                .rua( enderecoRequest.rua() )
                .numero( enderecoRequest.numero() )
                .bairro( enderecoRequest.bairro())
                .cidade( enderecoRequest.cidade())
                .estado( enderecoRequest.estado())
                .pais( enderecoRequest.pais())
                .build();
    }

    @Override
    public EnderecoResponse toResponse(Endereco endereco) {
        return EnderecoResponse.builder()
                .cep( endereco.getCep() )
                .rua( endereco.getRua() )
                .numero( endereco.getNumero() )
                .bairro( endereco.getBairro())
                .cidade( endereco.getCidade())
                .estado( endereco.getEstado())
                .pais( endereco.getPais())
                .build();
    }

    @Override
    public Collection<Endereco> findAll() {
        return repo.findAll();
    }

    public Endereco findById(Long id){return repo.findById(id).orElse(null);}

    @Override
    public Endereco save(Endereco endereco) {
        return repo.save( endereco );
    }

}
