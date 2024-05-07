package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.constraints.NotNull;

public record EnderecoRequest (

        @NotNull(message = "O CEP é obrigatório")
        String cep,

        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String pais
) {}