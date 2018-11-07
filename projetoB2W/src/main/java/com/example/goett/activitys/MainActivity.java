package com.example.goett.activitys;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.goett.model.PlanetaResults;
import com.example.goett.model.Planetas;
import com.example.goett.myapplication.R;
import com.example.goett.service.APIInterface;
import com.example.goett.service.ServiceGenerator;
import com.example.goett.utils.JDialogCarregando;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
    APIInterface apiInterface;
    Timer timer = null;
    //PlanetasLista planetList;
    public ArrayList<Planetas> planetList;
    PlanetaResults planetResults;
    boolean temPlanetas = true;
    boolean executouTudo = false;
    int page = 1;
    String msg = "";
    int contador;

    JDialogCarregando progressDialog;

    public Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         setTitle("Loading...");

        progressDialog = new JDialogCarregando(MainActivity.this);

        progressDialog.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        progressDialog.inicializar("Please wait...");
        apiInterface = ServiceGenerator.getClient().create(APIInterface.class);

        //planetList = new PlanetasLista();
        planetList = new ArrayList<Planetas>();

        carregar();

         t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                next( planetList);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };




    }

    private void next(ArrayList<Planetas> planet) {
        double cont = (double) contador;
        Log.d("guambiarra",planet.size()+" "+cont );

        if (planet.size() < (contador/100 * 20)){
            Log.d("guambiarra","entrou 20");
            msg = "20";
        }else if (planet.size() < (cont/100 * 40)){
            msg = "40";
        }else if (planet.size() < (cont/100 * 60)){
            msg = "60";
        }else if (planet.size() < (cont/100 * 80)){
            msg = "80";
        }else if (planet.size() < (cont/100 * 100)){
            msg = "90";
        }
        progressDialog.setMessage(msg);


        if(planet.size() == contador){
            msg = "100";
            progressDialog.setMessage(msg);
            progressDialog.finalizar();
        }
    }

    public void carregar(){
        try {
            Call<PlanetaResults> callResults = apiInterface.doGetListResourcesCount();

            callResults.enqueue(new Callback<PlanetaResults>() {
                @Override
                public void onResponse(Call<PlanetaResults> callresults, Response<PlanetaResults> responseresults) {
                    if (responseresults.isSuccessful()) {
                        Log.d("DataCheck", new Gson().toJson(responseresults.body()));
                        try {
                            contador = (responseresults.body().getCount());
                            t.start();

                            while (page <= contador) {


                                //progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                                try {
                                    Call<Planetas> call = apiInterface.doGetListResources(page);

                                    call.enqueue(new Callback<Planetas>() {
                                        @Override
                                        public void onResponse(Call<Planetas> call, Response<Planetas> response) {

                                            if (response.isSuccessful()) {
                                                Log.d("DataCheck", new Gson().toJson(response.body()));
                                                String jsonObj;
                                                jsonObj = new Gson().toJson(response.body());
                                                try {

                            /*JSONArray jsonArr = new JSONArray(jsonObj);
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObjloop = jsonArr.getJSONObject(i);
                                Planetas planetaAux = new Planetas();
                                planetaAux.setName(jsonObjloop.getString("name"));
                                planetaAux.setDiameter(jsonObjloop.getString("diameter"));
                                planetaAux.setRotation_period(jsonObjloop.getString("rotation_period"));
                                planetaAux.setOrbital_period(jsonObjloop.getString("orbital_period"));
                                planetaAux.setGravity(jsonObjloop.getString("gravity"));
                                planetaAux.setPopulation(jsonObjloop.getString("population"));
                                planetaAux.setClimate(jsonObjloop.getString("climate"));
                                planetaAux.setTerrain(jsonObjloop.getString("terrain"));
                                planetaAux.setSurface_water(jsonObjloop.getString("surface_water"));
                                planetaAux.setResidents(Collections.singletonList(jsonObjloop.getString("residents")));
                                planetaAux.setFilms(Collections.singletonList(jsonObjloop.getString("films")));
                                planetaAux.setUrl(jsonObjloop.getString("url"));
                                planetaAux.setCreated(jsonObjloop.getString("created"));
                                planetaAux.setEdited(jsonObjloop.getString("edited"));*/


                                                    planetList.add(response.body());

                                                    Log.d("testeLoop", response.body().getName());


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                                // Log.d("teste",jsonObj);
                                                // planetList.add( gson.fromJson(new Gson().toJson(response.body().getResults()), Planetas.class));
                                            } else {
                                                System.out.println("ERROR " + response.raw().body());

                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<Planetas> call, Throwable t) {

                                            call.cancel();
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                page++;


                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Log.d("teste",jsonObj);
                        // planetList.add( gson.fromJson(new Gson().toJson(response.body().getResults()), Planetas.class));
                    } else {
                        System.out.println("ERROR " + responseresults.raw().body());

                    }

                }

                @Override
                public void onFailure(Call<PlanetaResults> call, Throwable t) {

                    call.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void imprimePLanetas (){

        Log.d("teste2","Entrou no imprime");
        if (planetList != null) {
            if (planetList.size() > 0) {
                for (int i = 0; i < planetList.size(); i++) {
                    Log.d("teste2", i + 1 + " " + planetList.get(i).getName());
                }

            }
        }else{
            Log.d("teste2", "null");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //imprimePLanetas();
        //Intent intent = new Intent(this, PlanetaActivity.class);
        //startActivity(intent);

    }


}





