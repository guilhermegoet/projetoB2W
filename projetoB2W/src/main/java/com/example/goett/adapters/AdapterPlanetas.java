package com.example.goett.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.goett.model.Planetas;
import com.example.goett.myapplication.R;

import java.util.ArrayList;

public class AdapterPlanetas extends ArrayAdapter<Planetas> {
    private ArrayList<Planetas> planetas;
    private Context context;

    public AdapterPlanetas(Context c, int textViewResourceId, ArrayList<Planetas> objects) {
        super(c, textViewResourceId, objects);
        this.planetas = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        // Verifica se a lista está vazia
        if( planetas != null ){
            // inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            view = inflater.inflate(R.layout.adapter_planetas, parent, false);

            TextView id = (TextView) view.findViewById(R.id.id);
            TextView nome = (TextView) view.findViewById(R.id.nome);
            TextView clima = (TextView) view.findViewById(R.id.clima);

            Planetas p = planetas.get( position );

            Log.d("testeadapter",position+" "+p.getUrl().substring(p.getUrl().length()-3,p.getUrl().length()-1).replace("/"," "));


            id.setText(""+p.getUrl().substring(p.getUrl().length()-3,p.getUrl().length()-1).replace("/"," "));
            nome.setText(""+p.getName());
            clima.setText(""+p.getClimate());

        }

        return view;

    }
}