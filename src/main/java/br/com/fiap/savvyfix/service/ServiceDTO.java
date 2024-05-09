package br.com.fiap.savvyfix.service;

import org.springframework.data.domain.Example;

import java.util.Collection;
import java.util.List;

public interface ServiceDTO<Entity, Request, Response>{

    Entity toEntity(Request request);

    Response toResponse(Entity entity);

    Collection<Entity> findAll();

    Entity save(Entity entity);

    List<Entity> findAll(Example<Entity> example);

}