package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.constraints.NotNull;

public record AbstractRequest (
        @NotNull(message = "O id é obrigatório")
        Long id
)
{
}