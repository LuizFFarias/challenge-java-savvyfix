package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.constraints.NotNull;

public record EnderecoRequest (


        @NotNull(message = "O cep é obrigatório")
        String cep,

        @NotNull(message = "A rua é obrigatória")
        String rua,

        @NotNull(message = "O número é obrigatório")
        String numero,

        @NotNull(message = "O bairro é obrigatório")
        String bairro,

        @NotNull(message = "A cidade é obrigatória")
        String cidade,

        @NotNull(message = "O estado é obrigatório")
        String estado,

        @NotNull(message = "O país é obrigatório")
        String pais
){
}