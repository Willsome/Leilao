package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioDeuCincoLancesException;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;

    private Double maiorLance = 0.0;
    private Double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance) {

        valida(lance);

        lances.add(lance);

        double valorLance = lance.getValor();
        if (primeiroLanceDefineMaiorEMenorLance(valorLance)) return;

        Collections.sort(lances);
        calculaMaiorLance(lance);
    }

    private boolean primeiroLanceDefineMaiorEMenorLance(double valorLance) {
        if (lances.size() == 1) {
            maiorLance = valorLance;
            menorLance = valorLance;
            return true;
        }
        return false;
    }

    private void valida(Lance lance) {

        double valorLance = lance.getValor();

        if (lanceForMenorQueUltimoLance(valorLance))
            throw new LanceMenorQueUltimoLanceException();

        if (temLances()) {
            Usuario usuarioDoLanceAtual = lance.getUsuario();

            if (usuarioForOMesmoDoUltimoLance(usuarioDoLanceAtual))
                throw new LanceSeguidoDoMesmoUsuarioException();

            if (usuarioDeuCincoLances(usuarioDoLanceAtual))
                throw new UsuarioDeuCincoLancesException();
        }

    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean usuarioDeuCincoLances(Usuario usuarioDoLanceAtual) {

        int lancesDoUsuario = 0;

        for (Lance l : lances) {
            Usuario usuarioDosLancesAnteriores = l.getUsuario();
            if (usuarioDosLancesAnteriores.equals(usuarioDoLanceAtual)) {
                lancesDoUsuario++;
                if(lancesDoUsuario == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean usuarioForOMesmoDoUltimoLance(Usuario usuarioDoLanceAtual) {
        Usuario usuarioDoUltimoLance = lances.get(0).getUsuario();
        if (usuarioDoLanceAtual.equals(usuarioDoUltimoLance)) {
            return true;
        }
        return false;
    }

    private boolean lanceForMenorQueUltimoLance(double valorLance) {
        if (maiorLance > valorLance) {
            return true;
        }
        return false;
    }

    private void calculaMenorLance(Lance lance) {
        if (lance.getValor() < menorLance) {
            menorLance = lance.getValor();
        }
    }

    private void calculaMaiorLance(Lance lance) {
        if (lance.getValor() > maiorLance) {
            maiorLance = lance.getValor();
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getMaiorLance() {
        return maiorLance;
    }

    public Double getMenorLance() {
        return menorLance;
    }

    public List<Lance> getTresMaioresLances() {

        int quantidadeDeLances = lances.size();

        if (lances.size() > 3) {
            quantidadeDeLances = 3;
        }

        return lances.subList(0, quantidadeDeLances);
    }

    public int quantidadeDeLances() {
        return lances.size();
    }
}
