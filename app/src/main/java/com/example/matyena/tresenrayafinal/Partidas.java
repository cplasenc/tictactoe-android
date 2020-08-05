package com.example.matyena.tresenrayafinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Partidas extends AppCompatActivity implements View.OnClickListener{

    Button bIndividual, bMultijugador;
    EditText editText;

    String partidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas);

        bIndividual = findViewById(R.id.bIndividual);
        bMultijugador = findViewById(R.id.bMultijugador);
        editText = findViewById(R.id.editText);

        bIndividual.setOnClickListener(this);
        bMultijugador.setOnClickListener(this);

        partidas = editText.getText().toString();

        if(partidas.length()>3){
            partidas="999";
        }
        if(partidas.equals("") || Integer.parseInt(partidas)<=0){
            partidas="1";
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bIndividual:

                Intent intent = new Intent(Partidas.this, Individual.class);
                intent.putExtra("Partidas", partidas);
                startActivity(intent);

                break;

            case R.id.bMultijugador:

                Intent intent2 = new Intent(Partidas.this, Multijugador.class);
                intent2.putExtra("Partidas", partidas);
                startActivity(intent2);

                break;
        }

    }
}
