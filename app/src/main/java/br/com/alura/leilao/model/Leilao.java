package br.com.alura.leilao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;

    private Double maiorLance = Double.NEGATIVE_INFINITY;
    private Double menorLance = Double.POSITIVE_INFINITY;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance) {
        lances.add(lance);
        Collections.sort(lances);
        calculaMaiorLance(lance);
        calculaMenorLance(lance);
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
}
