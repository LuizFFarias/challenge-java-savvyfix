package br.com.fiap.savvyfix.repository;

import br.com.fiap.savvyfix.entity.Atividades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadesRepository extends JpaRepository<Atividades, Long> {

    List<Atividades> findByPrecoVariado(float precoVariado);
}