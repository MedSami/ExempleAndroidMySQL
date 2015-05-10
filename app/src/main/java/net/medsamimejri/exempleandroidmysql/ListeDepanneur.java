package net.medsamimejri.exempleandroidmysql;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class ListeDepanneur extends ListActivity {
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/SelectToutDepanneur.php";
    public static final String strURL2 = "http://10.0.2.2:8080/Tunisie_Telecom/SupprimerDepanneur.php";

    ListView maList;
    String NumeroCinDepanneur = "";
    InputStream is = null;
    JSONArray ar;
    String nt, NumCinAutomobiliste, Action, result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_depanneur);
        maList = (ListView) findViewById(android.R.id.list);
        Intent data = this.getIntent();
        Action = data.getExtras().getString("Action");
        NumCinAutomobiliste = data.getExtras().getString("CinAutomobiliste");
        /****************************************************************/
        new SelectToutDepanneur().execute();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selection = l.getItemAtPosition(position).toString();

        NumeroCinDepanneur = "";
        for (int s = 15; s < 23; s++) {

            NumeroCinDepanneur += selection.charAt(s);

        }
        if (Action.equals("Modifier")) {
            Intent iNT = new Intent(ListeDepanneur.this,
                    ModifierDepanneur.class);
            iNT.putExtra("NumeroCin", NumeroCinDepanneur);
            startActivity(iNT);

            return;
        }
        if (Action.equals("Supprimer")) {
            new SupprimerDepanneur().execute();
            return;
        }

        if (Action.equals("Appeler")) {
            new AppelerDeppaneur().execute();

            return;
        }

        if (Action.equals("Demmande")) {
            Intent iNT = new Intent(ListeDepanneur.this, EnvoyerDemmande.class);
            iNT.putExtra("NumeroCinDepanneur", NumeroCinDepanneur);
            iNT.putExtra("NumeroCinAutomobiliste", NumCinAutomobiliste);
            startActivity(iNT);
            return;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static String[] CreateArray(int size) {
        String c[] = new String[size];
        return c;
    }

    private class SelectToutDepanneur extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeDepanneur.this,
                    "Progress", "Waiting Please", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, null, null, null, null, null, null, null, null,
                    null, null);

            try {
                is = js.Connect(strURL);
            } catch (Exception e) {
                Toast.makeText(ListeDepanneur.this,
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
                Toast.makeText(ListeDepanneur.this,
                        "Erreur Analyse de donnée ", Toast.LENGTH_LONG).show();
            }

            return ar;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result == null) {
                Toast.makeText(ListeDepanneur.this,
                        "Erreur Analyse de donnée ", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                String[] Depanneur = CreateArray(result.length());
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    String c = json_data.getString("Cin");
                    String np = json_data.getString("Nom_Prenom");
                    String nt = json_data.getString("NTel");
                    String mp = json_data.getString("MotDePasse");
                    Depanneur[i] = "Cin Dépanneur :" + c + " Nom Prénom:" + np;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListeDepanneur.this,
                        android.R.layout.simple_list_item_1, Depanneur);
                setListAdapter(adapter);

            } catch (JSONException e) {
                Toast.makeText(ListeDepanneur.this,
                        "Erreur Analyse de donnée ", Toast.LENGTH_LONG).show();
            }
        }

    }

    private class SupprimerDepanneur extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeDepanneur.this,
                    "Progress", "En cours ..", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL2, NumeroCinDepanneur, null, null, null, null, null,
                    null, null, null, null);
            js.initialisation(NumeroCinDepanneur, null, null, null, null, null,
                    null, null, null, null);
            try {
                js.Connect(strURL2);
                return true;
            } catch (Exception e) {
                Toast.makeText(ListeDepanneur.this,
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
                        ListeDepanneur.this);
                adb.setTitle("Info");
                adb.setMessage("Suppression terminer ");
                adb.setPositiveButton("Ok", null);
                adb.show();
            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeDepanneur.this);
                adb.setTitle("Info");
                adb.setMessage("Suppression Non terminer ");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }

    }

    private class AppelerDeppaneur extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeDepanneur.this,
                    "Progress", "En cours..", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, NumeroCinDepanneur, null, null, null, null, null,
                    null, null, null, null);
            js.initialisation(NumeroCinDepanneur, null, null, null, null, null,
                    null, null, null, null);

            try {

                is = js.Connect(strURL);
            } catch (Exception e) {
                Toast.makeText(ListeDepanneur.this,
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
                Toast.makeText(ListeDepanneur.this,
                        "Erreur Analyse de donnée", Toast.LENGTH_LONG)
                        .show();
            }
            return ar;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result==null){
                Toast.makeText(ListeDepanneur.this,
                        "Erreur Analyse de donnée", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            try{

                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    nt = json_data.getString("NTel");
                    Intent ci = new Intent(Intent.ACTION_CALL);
                    ci.setData(Uri.parse("tel:" + nt + ""));
                    startActivity(ci);
                }

            } catch (JSONException e) {
                Toast.makeText(ListeDepanneur.this,
                        "Erreur Analyse de donnée", Toast.LENGTH_LONG)
                        .show();		}
        }
    }

}
