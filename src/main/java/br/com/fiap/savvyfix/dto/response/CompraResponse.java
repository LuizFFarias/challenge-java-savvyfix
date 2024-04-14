package br.com.fiap.savvyfix.dto.response;

import br.com.fiap.savvyfix.dto.request.AbstractRequest;
import br.com.fiap.savvyfix.dto.request.AtividadesRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CompraResponse (

        Long id,
        String nomeProd,
        String qntdProd,
        float valorCompra,
        String especificacoes,

        ClienteResponse cliente,

        ProdutoResponse produto,

        AtividadesResponse atividades
){
}