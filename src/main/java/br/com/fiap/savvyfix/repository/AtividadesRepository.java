package br.com.fiap.savvyfix.repository;

import br.com.fiap.savvyfix.entity.Atividades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadesRepository extends JpaRepository<Atividades, Long> {
    Atividades findByClienteId(Long clienteId);
}