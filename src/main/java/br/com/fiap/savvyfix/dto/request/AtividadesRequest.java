package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;

public record AtividadesRequest (
        Float precoVariado,

        LocalTime horarioAtual,

        String localizacaoAtual,
        String  climaAtual,
        Integer qntdProcura,
        String demanda,

        @Valid
        AbstractRequest cliente,

        @Valid
        AbstractRequest produto
) {}