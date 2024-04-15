package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtividadesRequest (
        float precoVariado,

        @FutureOrPresent
        LocalTime horarioAtual,

        String localizacaoAtual,

        String  climaAtual,

        @NotNull( message = "O campo de procura não pode ser nulo")
        int qntdProcura,

        @NotNull( message = "O campo de demanda não pode ser nulo")
        String demanda,

        AbstractRequest cliente,

        AbstractRequest produto
){

}