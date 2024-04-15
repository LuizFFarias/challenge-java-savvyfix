package br.com.fiap.savvyfix.service;
import br.com.fiap.savvyfix.dto.request.AtividadesRequest;
import br.com.fiap.savvyfix.dto.response.AtividadesResponse;
import br.com.fiap.savvyfix.dto.response.ClienteResponse;
import br.com.fiap.savvyfix.entity.Atividades;
import br.com.fiap.savvyfix.entity.Cliente;
import br.com.fiap.savvyfix.entity.Endereco;
import br.com.fiap.savvyfix.repository.AtividadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AtividadesService implements  ServiceDTO<Atividades, AtividadesRequest, AtividadesResponse>{
    @Autowired
    private AtividadesRepository repo;

    @Autowired
    private ClienteService clienteService;

    @Override
    public Atividades toEntity(AtividadesRequest atividadesRequest) {

        Cliente cliente = null;

        if (Objects.nonNull( atividadesRequest.cliente().id() )) {
            cliente = clienteService.findById( atividadesRequest.cliente().id() );
        }

        return Atividades.builder()
                .precoVariado(atividadesRequest.precoVariado() )
                .horarioAtual( atividadesRequest.horarioAtual() )
                .localizacaoAtual( atividadesRequest.localizacaoAtual() )
                .climaAtual( atividadesRequest.climaAtual())
                .qntdProcura( atividadesRequest.qntdProcura())
                .demanda( atividadesRequest.demanda())
                .cliente( cliente )
                .build();
    }

    @Override
    public AtividadesResponse toResponse(Atividades atividades) {
        return AtividadesResponse.builder()
                .precoVariado( atividades.getPrecoVariado() )
                .horarioAtual( atividades.getHorarioAtual() )
                .localizacaoAtual( atividades.getLocalizacaoAtual() )
                .climaAtual( atividades.getClimaAtual())
                .qntdProcura( atividades.getQntdProcura())
                .demanda( atividades.getDemanda())
                .cliente( clienteService.toResponse(atividades.getCliente()) )
                .build();
    }

    @Override
    public Collection<Atividades> findAll() {
        return repo.findAll();
    }

    @Override
    public Atividades save(Atividades atividades) {
        return repo.save( atividades );
    }

    public List<Atividades> findByValor(float valorVariado) {
        return repo.findByValor( valorVariado );
    }


}
