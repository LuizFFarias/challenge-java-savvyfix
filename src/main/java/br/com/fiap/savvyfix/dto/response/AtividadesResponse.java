package br.com.fiap.savvyfix.dto.response;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record AtividadesResponse (

        float precoVariado,
        LocalTime horarioAtual,

        String localizacaoAtual,

        String  climaAtual,

        int qntdProcura,

        String demanda,

        ClienteResponse cliente,

        ProdutoResponse produto
){

}