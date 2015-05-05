package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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



public class AjouterAutomobiliste extends Activity {
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/AjoutAutomobiliste.php";
    EditText cin, nomPrenom, NTel, MotDePasse;
    Button ajouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_automobiliste);
        cin = (EditText) findViewById(R.id.editText1);
        nomPrenom = (EditText) findViewById(R.id.editText2);
        NTel = (EditText) findViewById(R.id.editText3);
        MotDePasse = (EditText) findViewById(R.id.editText4);
        ajouter = (Button) findViewById(R.id.button1);

        ajouter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (cin.getText().toString().equals("")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            AjouterAutomobiliste.this);
                    adb.setTitle("Vérifier");
                    adb.setMessage("saisir Cin Automobiliste");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    cin.requestFocus();

                } else if (nomPrenom.getText().toString().equals("")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            AjouterAutomobiliste.this);
                    adb.setTitle("Vérifier");
                    adb.setMessage("saisir Nom & Prénom Automobiliste");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    nomPrenom.requestFocus();
                } else if (NTel.getText().toString().equals("")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            AjouterAutomobiliste.this);
                    adb.setTitle("Vérifier");
                    adb.setMessage("saisir N° Tel Automobiliste");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    NTel.requestFocus();
                } else if (MotDePasse.getText().toString().equals("")) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            AjouterAutomobiliste.this);
                    adb.setTitle("Vérifier");
                    adb.setMessage("saisir Mot De Passe Automobiliste");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    MotDePasse.requestFocus();
                } else if (cin.getText().toString().length() != 8) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(
                            AjouterAutomobiliste.this);
                    adb.setTitle("Vérifier");
                    adb.setMessage("CIN doit etre de 8 chiffre");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                    cin.requestFocus();
                } else {

                    try {// convertir cin to integer
                        int NC = Integer.parseInt(cin.getText().toString());
                        try {
                            int NT = Integer
                                    .parseInt(NTel.getText().toString());
                            new AddAutomobiliste().execute();

                        } catch (Exception e) {
                            AlertDialog.Builder adb = new AlertDialog.Builder(
                                    AjouterAutomobiliste.this);
                            adb.setTitle("Vérifier");
                            adb.setMessage("N° TEL doit etre Numerique ");
                            adb.setPositiveButton("Ok", null);
                            adb.show();
                            cin.requestFocus();
                        }
                    } catch (Exception e) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(
                                AjouterAutomobiliste.this);
                        adb.setTitle("Vérifier");
                        adb.setMessage("CIN doit etre Numerique ");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
                        cin.requestFocus();
                    }
                }
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

    private class AddAutomobiliste extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(AjouterAutomobiliste.this,
                    "Progress", "Ajout En Cours ..", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, cin.getText().toString(), nomPrenom.getText()
                    .toString(), NTel.getText().toString(), MotDePasse
                    .getText().toString(), null, null, null, null,
                    null, null);
            js.initialisation(cin.getText().toString(), nomPrenom.getText()
                    .toString(), NTel.getText().toString(), MotDePasse
                    .getText().toString(), null, null, null, null, null, null);

            try {
                js.Connect(strURL);
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

            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result) {

                AlertDialog.Builder adb = new AlertDialog.Builder(
                        AjouterAutomobiliste.this);
                adb.setTitle("Ajout");
                adb.setMessage("Ajout Terminer");
                adb.setPositiveButton("Ok", null);
                adb.show();
            } else {

                AlertDialog.Builder adb = new AlertDialog.Builder(
                        AjouterAutomobiliste.this);
                adb.setTitle("Ajout");
                adb.setMessage("Ajout Non Terminer");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }
    }

}
