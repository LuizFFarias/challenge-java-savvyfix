package br.com.fiap.savvyfix.service;
import br.com.fiap.savvyfix.dto.request.CompraRequest;
import br.com.fiap.savvyfix.dto.response.CompraResponse;
import br.com.fiap.savvyfix.entity.Compra;
import br.com.fiap.savvyfix.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    @Autowired
    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<CompraResponse> getAllCompras() {
        return compraRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CompraResponse getCompraById(Long id) {
        return compraRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public CompraResponse createCompra(CompraRequest request) {
        Compra compra = toEntity(request);
        compra = compraRepository.save(compra);
        return toResponse(compra);
    }

    public CompraResponse updateCompra(Long id, CompraRequest request) {
        if (!compraRepository.existsById(id)) {
            return null;
        }
        Compra compra = toEntity(request);
        compra.setId(id);
        compra = compraRepository.save(compra);
        return toResponse(compra);
    }

    public void deleteCompra(Long id) {
        if (compraRepository.existsById(id)) {
            compraRepository.deleteById(id);
        }
    }

    private CompraResponse toResponse(Compra compra) {
        return new CompraResponse(
                compra.getId(),
                compra.getNomeProd(),
                compra.getQntdProd(),
                compra.getValorCompra(),
                compra.getEspecificacoes(),
                compra.getCliente(),
                compra.getProduto(),
                compra.getAtividades()
        );
    }

    private Compra toEntity(CompraRequest request) {
        return new Compra(
                null, // O ID será gerado automaticamente pelo banco de dados
                request.getNomeProd(),
                request.getQntdProd(),
                request.getValorCompra(),
                request.getEspecificacoes(),
                request.getCliente(),
                request.getProduto(),
                request.getAtividades()
        );
    }
}