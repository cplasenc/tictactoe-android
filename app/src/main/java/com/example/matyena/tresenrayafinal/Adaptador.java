package com.example.matyena.tresenrayafinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matyena.tresenrayafinal.entidades.Jugador;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<Jugador> listaJugadores;


    public Adaptador(Context context, int layout, ArrayList<Jugador> listaJugadores) {
        this.context = context;
        this.layout = layout;
        this.listaJugadores = listaJugadores;
    }

    @Override
    public int getCount() {
        return listaJugadores.size();
    }

    @Override
    public Object getItem(int posicion) {
        return listaJugadores.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return 0;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView tJugador, tPartidas, tVictorias, tEmpates;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.tJugador = row.findViewById(R.id.tJugador);
            holder.tPartidas = row.findViewById(R.id.tPartidas);
            holder.tVictorias = row.findViewById(R.id.tVictorias);
            holder.tEmpates = row.findViewById(R.id.tEmpates);
            holder.imageView = row.findViewById(R.id.imgFoto);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }

        Jugador jugador = listaJugadores.get(posicion);

        holder.tJugador.setText(jugador.getJugador());
        holder.tPartidas.setText(jugador.getPartidas());
        holder.tVictorias.setText(jugador.getVictorias());
        holder.tEmpates.setText(jugador.getEmpates());

        byte[] imagenJugador = jugador.getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagenJugador, 0, imagenJugador.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
