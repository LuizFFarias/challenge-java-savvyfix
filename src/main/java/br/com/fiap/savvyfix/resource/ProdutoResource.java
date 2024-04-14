package br.com.fiap.savvyfix.resource;

import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoResource(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getAllProdutos() {
        List<ProdutoResponse> produtoResponses = produtoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(toResponse(produto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> createProduto(@Valid @RequestBody ProdutoRequest request) {
        Produto produto = toEntity(request);
        produto = produtoRepository.save(produto);
        return ResponseEntity.ok(toResponse(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> updateProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Produto produto = toEntity(request);
        produto.setId(id);
        produto = produtoRepository.save(produto);
        return ResponseEntity.ok(toResponse(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProdutoResponse toResponse(Produto produto) {
        return ProdutoResponse.builder()
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .marca(produto.getMarca())
                .precoFixo(produto.getPrecoFixo())
                .build();
    }

    private Produto toEntity(ProdutoRequest request) {
        return Produto.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .marca(request.getMarca())
                .precoFixo(request.getPrecoFixo())
                .build();
    }
}
