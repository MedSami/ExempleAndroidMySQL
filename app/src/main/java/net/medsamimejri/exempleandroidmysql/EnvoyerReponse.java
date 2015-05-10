package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class EnvoyerReponse extends Activity {
    public static final String strURL1 = "http://10.0.2.2:8080/Tunisie_Telecom/AjoutReponse.php";
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/SelecteDemmande.php";
    EditText Demmande, cinAutomobiliste, CinDepanneur, Reponse;
    Button Valider;
    String d, NumeroCinAutomobiliste, NumeroCinDepanneur, result = "";
    InputStream is = null;
    JSONArray ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyer_reponse);
        cinAutomobiliste = (EditText) findViewById(R.id.editText1);
        CinDepanneur = (EditText) findViewById(R.id.editText2);
        Reponse = (EditText) findViewById(R.id.editText3);
        Demmande = (EditText) findViewById(R.id.editText4);

        Valider = (Button) findViewById(R.id.button1);
        Intent data = this.getIntent();
        NumeroCinDepanneur = data.getExtras().getString("CinDepanneur");
        NumeroCinAutomobiliste = data.getExtras().getString("CinAutomobiliste");
        cinAutomobiliste.setText(NumeroCinAutomobiliste);
        CinDepanneur.setText(NumeroCinDepanneur);
        CinDepanneur.setEnabled(false);
        cinAutomobiliste.setEnabled(false);
        new SelectDemmande().execute();

        Valider.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Reponse.getText().toString().equals("")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            EnvoyerReponse.this);
                    adb.setTitle("Vérifier");
                    adb.setMessage("saisir Reponse");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    Reponse.requestFocus();
                    return;
                }

                new AddReponse().execute();

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class SelectDemmande extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(EnvoyerReponse.this,
                    "Progress", "Waiting Please", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, NumeroCinAutomobiliste, NumeroCinDepanneur, null,
                    null, null, null, null, null, null, null);
            js.initialisation(NumeroCinAutomobiliste, NumeroCinDepanneur, null,
                    null, null, null, null, null, null, null);

            try {

                is = js.Connect(strURL);
            } catch (Exception e) {
                Toast.makeText(EnvoyerReponse.this,
                        "Error in http connection ", Toast.LENGTH_LONG).show();
                Log.e("log_tag", "Error chttp connection " + e.toString());
            }

            try {
                result = js.ConvertToString(is);
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }

            try {
                ar = js.Analyse(result);
            } catch (Exception e) {
                Toast.makeText(EnvoyerReponse.this, "Erreur Analyse de donnée",
                        Toast.LENGTH_LONG).show();
            }

            return ar;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result == null) {
                Toast.makeText(EnvoyerReponse.this, "Erreur Analyse de donnée",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    d = json_data.getString("Demmande");
                    Demmande.setText(d);
                    Demmande.setEnabled(false);
                }

            } catch (JSONException e) {
                Toast.makeText(EnvoyerReponse.this, "Erreur Analyse de donnée",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    private class AddReponse extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(EnvoyerReponse.this,
                    "Progress", "En cours ..", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL1, NumeroCinAutomobiliste, NumeroCinDepanneur,
                    Reponse.getText().toString(), null, null, null, null, null,
                    null, null);
            js.initialisation(NumeroCinAutomobiliste, NumeroCinDepanneur,
                    Reponse.getText().toString(), null, null, null, null, null,
                    null, null);

            try {
                js.Connect(strURL1);
                return true;

            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
                Toast.makeText(getApplicationContext(),
                        "Error in http connection", Toast.LENGTH_SHORT).show();

            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();

            if (result){
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        EnvoyerReponse.this);
                adb.setTitle("Reponse");
                adb.setMessage("Reponse Envoyer");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }else {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        EnvoyerReponse.this);
                adb.setTitle("Reponse");
                adb.setMessage("Reponse Non Envoyer");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }
    }
}
