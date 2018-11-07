package com.example.goett.utils;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.example.goett.activitys.MainActivity;
import com.example.goett.activitys.PlanetaActivity;


public class JDialogCarregando extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progress;
    final Context ctxt;

    public JDialogCarregando(final Context context) {

        ctxt = context;
    }

    public void setMessage (String msg){
        progress.setMessage(msg+"%");
    }

    public void inicializar( String messageConexao)
    {
        progress = new ProgressDialog(ctxt);
        progress.setCancelable(false);
        progress.setMessage("0%");
        progress.setTitle("Processing...");
        progress.setIndeterminate(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();


        /*Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 40000);

        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                progress.dismiss();
                Intent intent = new Intent(context, PlanetaActivity.class);
                intent.putExtra("dados", ((MainActivity)context).planetList);
                context.startActivity(intent);
                //((MainActivity)context).imprimePLanetas();
                ((MainActivity)context).finish();

            }
        });*/
    }
    public void finalizar()
    {

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 10);

        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                Intent intent = new Intent(ctxt, PlanetaActivity.class);
                intent.putExtra("dados", ((MainActivity)ctxt).planetList);
                ctxt.startActivity(intent);
                //((MainActivity)context).imprimePLanetas();
                ((MainActivity)ctxt).t.interrupt();
                ((MainActivity)ctxt).finish();
                progress.dismiss();

            }
        });
    }

    @Override
    protected Void doInBackground(Void... voids) {
        /*Log.d("background","teste");
        ((MainActivity)ctxt).carregar();
        Log.d("background","teste2");

        if (((MainActivity)ctxt).executouTudo)
            return null;*/

        return null;
    }

    /*@Override
    protected void onPostExecute(void p)
    {
        Log.d("postExecute","teste");
        progress.dismiss();
        Intent intent = new Intent(ctxt, PlanetaActivity.class);
        intent.putExtra("dados", p);
        ctxt.startActivity(intent);
        //((MainActivity)context).imprimePLanetas();
        ((MainActivity)ctxt).finish();


    }*/






}