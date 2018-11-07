package com.example.goett.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.goett.adapters.AdapterPlanetas;
import com.example.goett.model.PlanetInfoBasic;
import com.example.goett.model.Planetas;
import com.example.goett.myapplication.R;
import com.example.goett.service.APIInterface;
import com.example.goett.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanetaActivity extends AppCompatActivity {
    ArrayList<Planetas> planetas;
    private ListView listView;
    APIInterface apiInterface;
    private AdapterPlanetas pAdapter;
    int posicaoList = 0;
    String trecho = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planeta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Planetas");

        planetas = new ArrayList<Planetas>();

        apiInterface = ServiceGenerator.getClient().create(APIInterface.class);

        //planetas = listaPlanetas.getPlaneta();
        planetas = (ArrayList<Planetas>) getIntent().getSerializableExtra("dados");

        Collections.sort (planetas, new Comparator() {
            public int compare(Object o1, Object o2) {
                Planetas p1 = (Planetas) o1;
                Planetas p2 = (Planetas) o2;
                return Integer.parseInt(p1.getUrl().substring(p1.getUrl().length()-3,p1.getUrl().length()-1).replace("/","")) <
                        Integer.parseInt(p2.getUrl().substring(p2.getUrl().length()-3,p2.getUrl().length()-1).replace("/","")) ? -1 :
                        (Integer.parseInt(p1.getUrl().substring(p1.getUrl().length()-3,p1.getUrl().length()-1).replace("/",""))  >
                                Integer.parseInt(p2.getUrl().substring(p2.getUrl().length()-3,p2.getUrl().length()-1).replace("/","")) ? +1 : 0);
            }
        });
        //Log.d("testePlaneta",planetas.get(0).getName());

        listView = (ListView) findViewById(R.id.lista);

        pAdapter = new AdapterPlanetas(this,R.layout.adapter_planetas,planetas);
        listView.setAdapter(pAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                // TODO Auto-generated method stub


                posicaoList = posicao+1;
            }
        });

        imprimePLanetas ();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogNomeplaneta = new AlertDialog.Builder(PlanetaActivity.this);
                LinearLayout layout = new LinearLayout(PlanetaActivity.this);

                layout.setOrientation(LinearLayout.VERTICAL);

                alertDialogNomeplaneta.setTitle("Dados Planeta");
                alertDialogNomeplaneta.setMessage("");
                alertDialogNomeplaneta.setCancelable(false);



                final EditText editNomePlaneta = new EditText(PlanetaActivity.this);
                editNomePlaneta.setHint("Nome");
                layout.addView(editNomePlaneta);


                final EditText editClimaPlaneta = new EditText(PlanetaActivity.this);
                editClimaPlaneta.setHint("Clima");
                layout.addView(editClimaPlaneta);

                final EditText editTerrenoPlaneta = new EditText(PlanetaActivity.this);
                editTerrenoPlaneta.setHint("Terreno");
                layout.addView(editTerrenoPlaneta);



                alertDialogNomeplaneta.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener()  {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String clima = editClimaPlaneta.getText().toString().trim();

                        String nome = editNomePlaneta.getText().toString().trim();

                        String terreno = editTerrenoPlaneta.getText().toString().trim();


                        if (clima.isEmpty()||nome.isEmpty()||terreno.isEmpty()){
                            Toast.makeText(PlanetaActivity.this,"Preencha os campos", Toast.LENGTH_LONG).show();
                        }else {

                            Planetas planeta = new Planetas();

                            planeta.setName(nome);
                            planeta.setClimate(clima);
                            planeta.setTerrain(terreno);
                            /*planeta.setEdited("");
                            planeta.setCreated("");
                            planeta.setUrl("");
                            planeta.setFilms(null);
                            planeta.setResidents(null);
                            planeta.setSurface_water("");
                            planeta.setPopulation("");
                            planeta.setGravity("");
                            planeta.setOrbital_period("");
                            planeta.setDiameter("");*/
                            //String json = "{\"Success\":true,\"Message\":\"Invalid access token.\"}";

                            String json = "{\"name\":\""+nome+"\",\"climate\":\""+clima+"\",\"terrain\":\""+terreno+"\"}";
                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);



                            Log.d("json",json);


                            sendPost(body);

                        }
                    }
                });

                alertDialogNomeplaneta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialogNomeplaneta.setView(layout);
                alertDialogNomeplaneta.create();
                alertDialogNomeplaneta.show();

            }

        });


    }

    public void sendPost(RequestBody body) {
        apiInterface.savePost(body).enqueue(new Callback<Planetas>() {
            @Override
            public void onResponse(Call<Planetas> call, Response<Planetas> response) {
                Log.d("postCall request", call.request().toString());
                Log.d("postCall request header", call.request().headers().toString());
                Log.d("postResponse raw header", response.headers().toString());
                Log.d("postResponse raw", String.valueOf(response.raw().body()));
                Log.d("postResponse code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    Toast.makeText(PlanetaActivity.this,
                            "Post submitted to API, response code: " + String.valueOf(response.code()) , Toast.LENGTH_LONG)
                            .show();
                    Log.i("testePost", "post submitted to API." + response.body().toString());
                }
                else{
                    Log.i("testePost","deu ruim"+response.raw().body());
                }
            }

            @Override
            public void onFailure(Call<Planetas> call, Throwable t) {
                Log.e("testePost", "Unable to submit post to API.");
            }
        });
    }


    public void btnBuscar_OnClick(String txt){
        String busca = txt;
        ArrayList<Planetas> encontradas = new ArrayList<>();
        for(Planetas planeta : planetas){
            if(planeta.getName().contains(busca)) encontradas.add(planeta);
        }
        Log.d("testeBusca",busca);
        AdapterPlanetas adapter =
                new AdapterPlanetas(PlanetaActivity.this, R.layout.adapter_planetas, encontradas);
        listView.setAdapter(adapter);
    }

    public void btnVoltar_OnClick(){
        AdapterPlanetas adapter =
                new AdapterPlanetas(PlanetaActivity.this, R.layout.adapter_planetas, planetas);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_planeta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.item_voltar:
                btnVoltar_OnClick();
                return true;

            case R.id.item_procurar:

                AlertDialog.Builder alertDialogNomeplaneta = new AlertDialog.Builder(PlanetaActivity.this);
                LinearLayout layout = new LinearLayout(PlanetaActivity.this);

                layout.setOrientation(LinearLayout.VERTICAL);

                alertDialogNomeplaneta.setTitle("Pesquisar: ");
                alertDialogNomeplaneta.setMessage("");
                alertDialogNomeplaneta.setCancelable(false);



                final EditText editPesquisa = new EditText(PlanetaActivity.this);
                layout.addView(editPesquisa);

                alertDialogNomeplaneta.setPositiveButton("Procurar", new DialogInterface.OnClickListener()  {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txt = editPesquisa.getText().toString().trim();
                        if (txt.isEmpty()){
                            Toast.makeText(PlanetaActivity.this,"Preencha o campo", Toast.LENGTH_LONG).show();
                        }else {
                            trecho = txt;
                            btnBuscar_OnClick(trecho);
                        }
                    }
                });

                alertDialogNomeplaneta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialogNomeplaneta.setView(layout);
                alertDialogNomeplaneta.create();
                alertDialogNomeplaneta.show();




                return true;
            case R.id.item_remover:
                delete(posicaoList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void delete(int page) {
        apiInterface.deleteGist(1).enqueue(new Callback<Planetas>() {
            @Override
            public void onResponse(Call<Planetas> call, Response<Planetas> response) {
                Log.d("postCall request", call.request().toString());
                Log.d("postCall request header", call.request().headers().toString());
                Log.d("postResponse raw header", response.headers().toString());
                Log.d("postResponse raw", String.valueOf(response.raw().body()));
                Log.d("postResponse code", String.valueOf(response.code()));
                if(response.isSuccessful()) {
                    Toast.makeText(PlanetaActivity.this,
                            "Delete submitted to API, response code: " + String.valueOf(response.code()) , Toast.LENGTH_LONG)
                            .show();
                    Log.i("testePost", "post submitted to API." + response.body().toString());
                }
                else{
                    Log.i("testePost","deu ruim"+response.raw().body());
                }
            }

            @Override
            public void onFailure(Call<Planetas> call, Throwable t) {
                Log.e("testePost", "Unable to submit post to API.");
            }
        });
    }

    public  void imprimePLanetas (){

        Log.d("teste2","Entrou no imprime");
        if (planetas != null) {
            if (planetas.size() > 0) {
                for (int i = 0; i < planetas.size(); i++) {
                    Log.d("teste2", i + 1 + " " + planetas.get(i).getName());
                }

            }
        }else{
            Log.d("teste2", "null");
        }
    }

}
