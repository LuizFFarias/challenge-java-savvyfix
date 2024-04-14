package br.com.fiap.savvyfix.repository;

import br.com.fiap.savvyfix.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}