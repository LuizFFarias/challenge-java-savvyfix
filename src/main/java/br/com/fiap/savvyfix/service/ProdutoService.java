package br.com.fiap.savvyfix.service;

import br.com.fiap.savvyfix.dto.request.ProdutoRequest;
import br.com.fiap.savvyfix.dto.response.ProdutoResponse;
import br.com.fiap.savvyfix.entity.Produto;
import br.com.fiap.savvyfix.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponse> getAllProdutos() {
        return produtoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponse getProdutoById(Long id) {
        return produtoRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public ProdutoResponse createProduto(ProdutoRequest request) {
        Produto produto = toEntity(request);
        produto = produtoRepository.save(produto);
        return toResponse(produto);
    }

    public ProdutoResponse updateProduto(Long id, ProdutoRequest request) {
        if (!produtoRepository.existsById(id)) {
            return null;
        }
        Produto produto = toEntity(request);
        produto.setId(id);
        produto = produtoRepository.save(produto);
        return toResponse(produto);
    }

    public void deleteProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        }
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getNome(),
                produto.getDescricao(),
                produto.getMarca(),
                produto.getPrecoFixo()
        );
    }

    private Produto toEntity(ProdutoRequest request) {
        return new Produto(
                null, // O ID será gerado automaticamente pelo banco de dados
                request.getNome(),
                request.getDescricao(),
                request.getMarca(),
                request.getPrecoFixo()
        );
    }
}
