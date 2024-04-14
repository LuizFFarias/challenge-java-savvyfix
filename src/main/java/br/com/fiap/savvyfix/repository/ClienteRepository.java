package br.com.fiap.savvyfix.repository;

import br.com.fiap.savvyfix.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByCpf(int cpf);
}