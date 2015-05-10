package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class Reponse extends Activity {
    TextView NomDepanneur, rep;
    String NumeroCinDepanneur, n, r, NumeroCinAutomobiliste;
    String result = "";
    InputStream is = null;
    JSONArray ar;

    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/SelecteReponse.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse);
        NomDepanneur = (TextView) findViewById(R.id.textView2);
        rep = (TextView) findViewById(R.id.textView3);
        Intent data = this.getIntent();
        NumeroCinAutomobiliste = data.getExtras().getString("CinAutomobiliste");
        NumeroCinDepanneur = data.getExtras().getString("CinDepanneur");

        new SelectReponse().execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private class SelectReponse extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(Reponse.this, "Progress",
                    "Waiting Please..", false, false);
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
                Toast.makeText(Reponse.this, "Error in http connection ",
                        Toast.LENGTH_LONG).show();
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
                Toast.makeText(Reponse.this, "Erreur Analyse de donnée",
                        Toast.LENGTH_LONG).show();
            }
            return ar;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if(result==null){
                Toast.makeText(Reponse.this, "Erreur Analyse de donnée",
                        Toast.LENGTH_LONG).show();
                return;
            }
            try{
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    r = json_data.getString("Reponse");
                    n = json_data.getString("Nom_Prenom");
                    rep.setText(r);
                    NomDepanneur.setText(n);
                    NomDepanneur.setEnabled(false);
                    rep.setEnabled(false);
                }

            } catch (JSONException e) {
                Toast.makeText(Reponse.this, "Erreur Analyse de donnée",
                        Toast.LENGTH_LONG).show();			}
        }

    }
}
