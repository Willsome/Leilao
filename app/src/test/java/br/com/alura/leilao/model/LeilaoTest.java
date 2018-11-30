package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioDeuCincoLancesException;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeilaoTest {

    private static final double DELTA = 0.0001;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Leilao CONSOLE = new Leilao("Console");
    private Usuario USUARIO_1 = new Usuario("Usuário 1");

    @Test
    public void getDescricao_QuandoRecebeDescricao_DevolveDescricao() {
        // Executa ação esperada
        String descricaoRetornada = CONSOLE.getDescricao();

        // Executar o teste para um resultado esperado
//         assertEquals("Console", descricaoRetornada);

        assertThat(descricaoRetornada, is(equalTo("Console")));
    }

    @Test
    public void getMaiorLance_QuandoRecebeUmLance_DevolveMaiorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar o teste para um resultado esperado
//        assertEquals(200.0, CONSOLE.getMaiorLance(), DELTA);

        assertThat(CONSOLE.getMaiorLance(), is(closeTo(200.0, DELTA)));
    }

    @Test
    public void getMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente_DevolveMaiorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Usuário 2"), 300.0));

        // Executar o teste para um resultado esperado
        assertEquals(300.0, CONSOLE.getMaiorLance(), DELTA);
    }

    @Test
    public void getMenorLance_QuandoRecebeUmLance_DevolveMenorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));

        // Executar o teste para um resultado esperado
        assertEquals(200.0, CONSOLE.getMenorLance(), DELTA);
    }

    @Test
    public void getMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente_DevolveMenorLance() {
        // Executar ação esperada
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Usuário 2"), 300.0));

        // Executar o teste para um resultado esperado
        assertEquals(200.0, CONSOLE.getMenorLance(), DELTA);
    }

    @Test
    public void getTresMaioresLances_QuandoRecebeExatosTresLances_DevolveTresMaioresLances() {

        // Criar cenário de teste
        CONSOLE.propoe(new Lance(new Usuario("Bete Banana Confete"), 100.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Bete Banana Confete"), 300.0));

        // Executar ação esperada
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.getTresMaioresLances();

        // Executar teste
//        assertEquals(3, tresMaioresLancesDevolvidos.size());
//        assertThat(tresMaioresLancesDevolvidos, hasSize(equalTo(3)));

//        assertEquals(300.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
//        assertThat(tresMaioresLancesDevolvidos, hasItem((new Lance(new Usuario("Bete Banana Confete"), 100.0))));

//        assertEquals(200.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
//        assertEquals(100.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);

        assertThat(tresMaioresLancesDevolvidos, both(
                Matchers.<Lance>hasSize(3))
                .and(
                        contains(
                                new Lance(new Usuario("Bete Banana Confete"), 300.0),
                                new Lance(USUARIO_1, 200.0),
                                new Lance(new Usuario("Bete Banana Confete"), 100.0)
                        )
                )
        );
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
        CONSOLE.propoe(new Lance(new Usuario("Bete"), 500.0));

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
        final Usuario USUARIO_2 = new Usuario("Belepoc");
        CONSOLE.propoe(new Lance(USUARIO_2, 500.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 600.0));
        CONSOLE.propoe(new Lance(USUARIO_2, 5600.0));

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

    @Test
    public void deve_DevolverMaiorLanceComoZero_QuandoNenhumLanceForFeito() {
        // Executar ação esperada
        Double maiorLance = CONSOLE.getMaiorLance();

        assertEquals(0.0, maiorLance, DELTA);
    }

    @Test
    public void deve_DevolverMenorLanceComoZero_QuandoNenhumLanceForFeito() {
        // Executar ação esperada
        Double menorLance = CONSOLE.getMenorLance();

        assertEquals(0.0, menorLance, DELTA);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoOValorDoLanceForMenorQueOValorDoMaiorLance() {
//        exception.expect(RuntimeException.class);
//        exception.expectMessage("O valor do lance é menor que o do último lance");

        CONSOLE.propoe(new Lance(USUARIO_1, 300.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void deve_LancarException_QuandoForOMesmoUsuarioDoUltimoLance() {
//        exception.expect(RuntimeException.class);
//        exception.expectMessage("O usuário é o mesmo do último lance");

        CONSOLE.propoe(new Lance(USUARIO_1, 200.0));
        CONSOLE.propoe(new Lance(USUARIO_1, 300.0));
    }

    @Test(expected = UsuarioDeuCincoLancesException.class)
    public void deve_LancarException_QuandoOMesmoUsuarioFizerMaisDeCincoLances() {
//        exception.expect(RuntimeException.class);private
//        exception.expectMessage("O usuário já deu cinco lances");

        final Usuario USUARIO_2 = new Usuario("Fofão");

        final Leilao console = new LeilaoBuilder("Console")
                .lance(USUARIO_1, 100.0)
                .lance(USUARIO_2, 200.0)
                .lance(USUARIO_1, 300.0)
                .lance(USUARIO_2, 400.0)
                .lance(USUARIO_1, 500.0)
                .lance(USUARIO_2, 600.0)
                .lance(USUARIO_1, 700.0)
                .lance(USUARIO_2, 800.0)
                .lance(USUARIO_1, 900.0)
                .lance(USUARIO_2, 1000.0)
                .build();

        console.propoe(new Lance(USUARIO_1, 1100.0)); // EXCEPTION
    }

}