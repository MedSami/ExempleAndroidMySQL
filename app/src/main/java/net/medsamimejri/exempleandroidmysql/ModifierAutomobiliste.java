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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View.OnClickListener;

import java.io.InputStream;


public class ModifierAutomobiliste extends Activity {
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/ModifierAutomobiliste.php";
    public static final String strURL1 = "http://10.0.2.2:8080/Tunisie_Telecom/SelectAutomobiliste.php";

    String NumCin, NomPrenom, NumTel, Pss;
    EditText cin, nomprenom, ntel, motdepasse;
    Button Valider;
    InputStream is = null;
    String nt, Action, result = "";
    JSONArray ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_automobiliste);
        cin = (EditText) findViewById(R.id.editText1);
        nomprenom = (EditText) findViewById(R.id.editText2);
        ntel = (EditText) findViewById(R.id.editText3);
        motdepasse = (EditText) findViewById(R.id.editText4);
        Valider = (Button) findViewById(R.id.button1);
        Intent data = this.getIntent();
        NumCin = data.getExtras().getString("NumeroCin");

        new Task().execute();

        Valider.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new Modifier().execute();
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

    private class Task extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ModifierAutomobiliste.this,
                    "Progress", "Waiting Please..", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO Auto-generated method stub
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL1, NumCin, null, null, null, null, null, null, null,
                    null, null);

            js.initialisation(NumCin, null, null, null, null, null, null, null,
                    null, null);

            try {

                is = js.Connect(strURL1);
            } catch (Exception e) {
                Toast.makeText(ModifierAutomobiliste.this,
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
                Toast.makeText(getApplicationContext(),
                        "Erreur Analyse de donnée", Toast.LENGTH_SHORT).show();
            }

            return ar;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result == null) {
                Toast.makeText(getApplicationContext(),
                        "Aucune donnée obtenir", Toast.LENGTH_SHORT).show();
            }
            try {
                for (int i = 0; i < ar.length(); i++) {
                    JSONObject json_data = ar.getJSONObject(i);
                    String c = json_data.getString("Cin");
                    String np = json_data.getString("Nom_Prenom");
                    String nt = json_data.getString("NTel");
                    String mp = json_data.getString("MotDePasse");

                    cin.setText(c);
                    nomprenom.setText(np);
                    ntel.setText(nt);
                    motdepasse.setText(mp);
                    cin.setEnabled(false);
                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),
                        "Erreur Analyse de donnée", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class Modifier extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ModifierAutomobiliste.this,
                    "Progres", "Modification en cours..", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, NumCin, nomprenom.getText().toString(), ntel
                    .getText().toString(), motdepasse.getText()
                    .toString(), null, null, null, null, null, null);

            js.initialisation(NumCin, nomprenom.getText().toString(), ntel
                            .getText().toString(), motdepasse.getText().toString(),
                    null, null, null, null, null, null);
            try {
                js.Connect(strURL);
                return true;

            } catch (Exception e) {
                Toast.makeText(ModifierAutomobiliste.this,
                        "Error in http connection ", Toast.LENGTH_LONG).show();
                Log.e("log_tag", "Error chttp connection " + e.toString());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ModifierAutomobiliste.this);
                adb.setTitle("Info");
                adb.setMessage("Modification terminer ");
                adb.setPositiveButton("Ok", null);
                adb.show();
            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ModifierAutomobiliste.this);
                adb.setTitle("Info");
                adb.setMessage("Modification non terminer ");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }
    }
}
