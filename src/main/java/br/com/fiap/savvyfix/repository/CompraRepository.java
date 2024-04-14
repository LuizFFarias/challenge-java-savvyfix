package br.com.fiap.savvyfix.repository;

import br.com.fiap.savvyfix.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}