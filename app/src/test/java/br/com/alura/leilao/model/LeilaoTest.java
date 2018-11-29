package br.com.alura.leilao.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LeilaoTest {

    public static final double DELTA = 0.0001;
    private Leilao CONSOLE = new Leilao("Console");
    private Usuario USUARIO_1 = new Usuario("Usuário 1");

    @Test
    public void getDescricao_QuandoRecebeDescricao_DevolveDescricao() {
        // Executa ação esperada
        String descricaoRetornada = CONSOLE.getDescricao();

        // Executar o teste para um resultado esperado
         assertEquals("Console", descricaoRetornada);
    }


    @Test
    public void getMaiorLance_QuandoRecebeUmLance_DevolveMaiorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar o teste para um resultado esperado
        assertEquals(200.0, CONSOLE.getMaiorLance(), 0.0001);
    }

    @Test
    public void getMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente_DevolveMaiorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Usuário 2"), 300.0));

        // Executar o teste para um resultado esperado
        assertEquals(300.0, CONSOLE.getMaiorLance(), 0.0001);
    }

    @Test
    public void getMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente_DevolveMaiorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(new Usuario("Usuário 2"), 300.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar o teste para um resultado esperado
        assertEquals(300.0, CONSOLE.getMaiorLance(), 0.0001);
    }

    @Test
    public void getMenorLance_QuandoRecebeUmLance_DevolveMenorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar o teste para um resultado esperado
        assertEquals(200.0, CONSOLE.getMenorLance(), 0.0001);
    }

    @Test
    public void getMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente_DevolveMenorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Usuário 2"), 300.0));

        // Executar o teste para um resultado esperado
        assertEquals(200.0, CONSOLE.getMenorLance(), 0.0001);
    }

    @Test
    public void getMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente_DevolveMenorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(new Usuario("Usuário 2"), 300.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar o teste para um resultado esperado
        assertEquals(200.0, CONSOLE.getMenorLance(), 0.0001);
    }

    @Test
    public void getTresMaioresLances_QuandoRecebeExatosTresLances_DevolveTresMaioresLances() {

        // Criar cenário de teste
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Bete Banana Confete"), 100.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 400.0));

        // Executar ação esperada
        List<Lance> lances = CONSOLE.getTresMaioresLances();

        // Executar teste
        assertEquals(3, lances.size());

        assertEquals(400.0, lances.get(0).getValor(), DELTA);
        assertEquals(200.0, lances.get(1).getValor(), DELTA);
        assertEquals(100.0, lances.get(2).getValor(), DELTA);
    }

    @Test
    public void getTresMaioresLances_QuandoNapRecebeLance_NaoDevolveLance() {

        // Executar ação esperada
        List<Lance> lances = CONSOLE.getTresMaioresLances();

        // Executar teste
        assertEquals(0, lances.size());
    }

    @Test
    public void getTresMaioresLances_QuandoRecebeApenasUmLance_DevolveApenasUmLance() {

        // Criar cenário de teste
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar ação esperada
        List<Lance> lances = CONSOLE.getTresMaioresLances();

        // Executar teste
        assertEquals(1, lances.size());

        assertEquals(200.0, lances.get(0).getValor(), DELTA);
    }

    @Test
    public void getTresMaioresLances_QuandoRecebeDoisLances_DevolveDoisMaioresLances() {

        // Criar cenário de teste
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 500.0));

        // Executar ação esperada
        List<Lance> lances = CONSOLE.getTresMaioresLances();

        // Executar teste
        assertEquals(2, lances.size());

        assertEquals(500.0, lances.get(0).getValor(), DELTA);
        assertEquals(200.0, lances.get(1).getValor(), DELTA);
    }

    @Test
    public void getTresMaioresLances_QuandoRecebeMaisDeTresLances_DevolveTresMaioresLances() {

        // Criar cenário de teste
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Belepoc"), 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Belepoc"), 5600.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 600.0));

        // Executar ação esperada
        List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = CONSOLE.getTresMaioresLances();

        // Executar teste
        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());

        assertEquals(5600.0, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        CONSOLE.propoe(new Lance(USUARIO_1, 6500.0));

        // Executar ação esperada
        List<Lance> tresMaioresLancesDevolvidosParaCincoLances = CONSOLE.getTresMaioresLances();

        // Executar teste
        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());

        assertEquals(6500.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(5600.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);

    }

}