package br.com.zgsolucoes.dominio;

import br.com.zgsolucoes.entidades.Produto;

import java.math.BigDecimal;

public interface Desconto {

    BigDecimal ativarPromocao (Produto produto, BigDecimal quantidade);

}
