package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);

        Intent dadosRecebidos = getIntent();

        if(dadosRecebidos.hasExtra("leilao")){

            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");

            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            TextView maiorLance = findViewById(R.id.lances_leilao_maior_lance);
            TextView menorLance = findViewById(R.id.lances_leilao_menor_lance);
            TextView maioresLances = findViewById(R.id.lances_leilao_maiores_lances);

            descricao.setText(leilao.getDescricao());
            maiorLance.setText(String.valueOf(leilao.getMaiorLance()));
            menorLance.setText(String.valueOf(leilao.getMenorLance()));

            StringBuilder sb = new StringBuilder();

            for (Lance lance : leilao.getTresMaioresLances()) {
                sb.append(lance.getValor()).append("\n");
            }

            String maioresLancesEmTexto = sb.toString();

            maioresLances.setText(maioresLancesEmTexto);
        }
    }
}
