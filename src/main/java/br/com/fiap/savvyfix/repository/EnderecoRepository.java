package br.com.fiap.savvyfix.repository;

import br.com.fiap.savvyfix.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}