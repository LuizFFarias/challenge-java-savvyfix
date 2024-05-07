package br.com.fiap.savvyfix.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EnderecoResponse (

        String cep,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String pais
) {}