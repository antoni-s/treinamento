package br.com.zgsolucoes;
import br.com.zgsolucoes.dominio.util.Extrator;
import br.com.zgsolucoes.dominio.util.LerArquivo;
import br.com.zgsolucoes.dominio.util.csv.ExtratorCSV;
import br.com.zgsolucoes.dominio.util.regex.ExtratorRegex;
import br.com.zgsolucoes.entidades.Produto;
import br.com.zgsolucoes.entidades.Promocao;
import br.com.zgsolucoes.persistencia.Conexao;
import br.com.zgsolucoes.persistencia.ProdutoDAO;
import br.com.zgsolucoes.persistencia.PromocaoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Principal {

    private final String CREATE_TABLE_PRODUTO = "CREATE TABLE IF NOT EXISTS PRODUTOS(ID INTEGER NOT NULL, " +
            "DESCRICAO VARCHAR(20) NOT NULL, PRECO FLOAT NOT NULL, FKPROMACAO INTEGER NOT NULL, PRIMARY KEY (ID));";
    private final String CREATE_TABLE_PROMOCAO = "CREATE TABLE IF NOT EXISTS PROMOCAO( ID INTEGER NOT NULL, " +
            "DESCRICAO VARCHAR(20) NOT NULL, OBS VARCHAR(50), QTD_ATIVACAO INT NOT NULL, PRECO_FINAL INT, " +
            "QTD_PAGA INT, PRIMARY KEY (ID));";

    public void iniciarBD() {

        try(Connection conexao = Conexao.getConexao();
            PreparedStatement criarProduto = conexao.prepareStatement(CREATE_TABLE_PRODUTO);
            PreparedStatement criarPromocao = conexao.prepareStatement(CREATE_TABLE_PROMOCAO);){
            criarPromocao.execute();
            criarProduto.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final String CAMINHO_CSV = "dados-para-testes/promoções.csv";
    private final String CAMINHO_TXT = "dados-para-testes/Arquivo_dados_checkout.txt";
    private final String REGRA_ID = "(?<=id: )\\d+(<=|)";
    private final String REGRA_DESCRICAO = "(?<=descricao: )\\w+(<=|)";
    private final String REGRA_VALOR = "(?<=valor: )\\d+.?\\d+(<=|)";
    private final String REGRA_PROMOCAO = "(?<=promocao: ).?\\d+(<=|)";


    public void popularBanco() throws IOException {

        LerArquivo lerArquivo = new LerArquivo();
        Extrator extrator;
        PromocaoDAO promocaoDAO = new PromocaoDAO();
        Promocao objPromocao = new Promocao();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        List<String> promocoes, produtos, objeto = new ArrayList<>();
        promocoes = lerArquivo.lerArquivo(CAMINHO_CSV);
        extrator = new ExtratorCSV();

        for (int i = 1; i < promocoes.size(); i++) {
            extrator.setTexto(promocoes.get(i));
            objeto = ((ExtratorCSV) extrator).getResultado(",");
            objPromocao.setId(parseInt(objeto.get(0)));
            objPromocao.setDescricao(objeto.get(1));
            objPromocao.setObservacao(objeto.get(2));
            objPromocao.setQtdeAtivacao(objeto.get(3).equals("") ? 0 : Integer.parseInt(objeto.get(3)));
            objPromocao.setPrecoFinal(objeto.get(4).equals("") ? 0 : Integer.parseInt(objeto.get(4)));
            if (objeto.get(4).equals("")) {
                objPromocao.setQtdePaga(parseInt(objeto.get(5)));
            } else {
                objPromocao.setQtdePaga(0);
            }
            promocaoDAO.inserirPromocao(objPromocao);
        }

        produtos = lerArquivo.lerArquivo(CAMINHO_TXT);
        extrator = new ExtratorRegex();
        Produto produto = new Produto();
        String retorno;

        for (int i = 0; i < produtos.size(); i++) {
            extrator.setTexto(produtos.get(i));

            retorno = extrator.getResultado(REGRA_ID).toString();
            produto.setId(Integer.parseInt(retorno.subSequence(1, retorno.length()-1).toString()));
            retorno = extrator.getResultado(REGRA_DESCRICAO).toString();
            produto.setDescricao(retorno.subSequence(1, retorno.length()-1).toString());
            retorno = extrator.getResultado(REGRA_VALOR).toString();
            produto.setValor(new BigDecimal(Float.parseFloat(retorno.subSequence(1, retorno.length()-1).toString())));
            retorno = extrator.getResultado(REGRA_PROMOCAO).toString();
            produto.setPromocao(promocaoDAO.obterPromocao(Integer.parseInt(retorno.subSequence(1, retorno.length()-1).toString())));
            produtoDAO.inserirProdutos(produto);
        }
    }
}
