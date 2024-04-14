package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.CompraRequest;
import br.com.fiap.savvyfix.dto.response.CompraResponse;
import br.com.fiap.savvyfix.entity.Compra;
import br.com.fiap.savvyfix.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/compras")
public class CompraResource {

    private final CompraRepository compraRepository;

    @Autowired
    public CompraResource(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @GetMapping
    public ResponseEntity<List<CompraResponse>> getAllCompras() {
        List<CompraResponse> compraResponses = compraRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(compraResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> getCompraById(@PathVariable Long id) {
        return compraRepository.findById(id)
                .map(compra -> ResponseEntity.ok(toResponse(compra)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CompraResponse> createCompra(@Valid @RequestBody CompraRequest request) {
        Compra compra = toEntity(request);
        compra = compraRepository.save(compra);
        return ResponseEntity.ok(toResponse(compra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraResponse> updateCompra(@PathVariable Long id, @Valid @RequestBody CompraRequest request) {
        if (!compraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Compra compra = toEntity(request);
        compra.setId(id);
        compra = compraRepository.save(compra);
        return ResponseEntity.ok(toResponse(compra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        if (!compraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        compraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CompraResponse toResponse(Compra compra) {
        return CompraResponse.builder()
                .id(compra.getId())
                .nomeProd(compra.getNomeProd())
                .qntdProd(compra.getQntdProd())
                .valorCompra(compra.getValorCompra())
                .especificacoes(compra.getEspecificacoes())
                .cliente(compra.getCliente())
                .produto(compra.getProduto())
                .atividades(compra.getAtividades())
                .build();
    }

    private Compra toEntity(CompraRequest request) {
        return Compra.builder()
                .nomeProd(request.getNomeProd())
                .qntdProd(request.getQntdProd())
                .valorCompra(request.getValorCompra())
                .especificacoes(request.getEspecificacoes())
                .cliente(request.getCliente())
                .produto(request.getProduto())
                .atividades(request.getAtividades())
                .build();
    }
}
