package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
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


public class ListeAutomobiliste extends ListActivity {
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/SelectToutAutomobiliste.php";
    public static final String strURL1 = "http://10.0.2.2:8080/Tunisie_Telecom/SupprimerAutomobiliste.php";
    public static final String strURL2 = "http://10.0.2.2:8080/Tunisie_Telecom/SelectAutomobiliste.php";

    ListView maList;
    String NumeroCinAutomobiliste = "";
    InputStream is = null;
    JSONArray ar;
    String nt, Action, result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_automobiliste);
        maList = (ListView) findViewById(android.R.id.list);
        Intent data = this.getIntent();
        Action = data.getExtras().getString("Action");

        try {
            new ListAuto().execute();

        } catch (Exception e) {
            Log.e("log_tag", "Error Array Adapter" + e.toString());
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selection = l.getItemAtPosition(position).toString();

        NumeroCinAutomobiliste = "";
        for (int s = 19; s < 27; s++) {

            NumeroCinAutomobiliste += selection.charAt(s);

        }
        Toast.makeText(ListeAutomobiliste.this, NumeroCinAutomobiliste,
                Toast.LENGTH_LONG).show();
        if (Action.equals("Modifier")) {

            Intent iNT = new Intent(ListeAutomobiliste.this,
                    ModifierAutomobiliste.class);
            iNT.putExtra("NumeroCin", NumeroCinAutomobiliste);

            startActivity(iNT);

            return;
        }
        if (Action.equals("Supprimer")) {
            new SupprimerAutomobiliste().execute();

            return;
        }

        if (Action.equals("Appeler")) {
            new AppelerAuto().execute();

            return;
        }

        if (Action.equals("Localiser")) {
            new LocaliserAuto().execute();

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

    private class ListAuto extends AsyncTask<Void, Void, JSONArray> {
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeAutomobiliste.this,
                    "Progress...", "Waiting please ...", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, null, null, null, null, null, null, null, null,
                    null, null);
            try {
                is = js.Connect(strURL);
            } catch (Exception e) {
                Toast.makeText(ListeAutomobiliste.this,
                        "Error in http connection", Toast.LENGTH_LONG).show();

            }

            try {
                result = js.ConvertToString(is);
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result" + e.toString());
            }

            try {
                ar = js.Analyse(result);
            } catch (Exception e) {
                Toast.makeText(ListeAutomobiliste.this,
                        "Erreur Analyse de donnée", Toast.LENGTH_LONG).show();
            }
            return ar;

        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result == null) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeAutomobiliste.this);
                adb.setTitle("Vérifier");
                adb.setMessage("No data returned from db");
                adb.setPositiveButton("Ok", null);
                adb.show();
                return;
            }

            try {
                String Automobiliste[] = CreateArray(result.length());
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    String c = json_data.getString("Cin");
                    String np = json_data.getString("Nom_Prenom");
                    String nt = json_data.getString("NTel");
                    String mp = json_data.getString("MotDePasse");
                    Automobiliste[i] = "Cin Automobiliste :" + c
                            + " Nom Prénom:" + np;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            ListeAutomobiliste.this,
                            android.R.layout.simple_list_item_1, Automobiliste);
                    setListAdapter(adapter);
                }

            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        }

    }

    private class SupprimerAutomobiliste extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeAutomobiliste.this,
                    "Progress", "En cour de supprimer ...", false, false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO Auto-generated method stub

            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL1, NumeroCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);

            js.initialisation(NumeroCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);
            try {
                js.Connect(strURL1);

                return true;

            } catch (Exception e) {
                Toast.makeText(ListeAutomobiliste.this,
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
                        ListeAutomobiliste.this);
                adb.setTitle("Info");
                adb.setMessage("Suppression terminer ");
                adb.setPositiveButton("Ok", null);
                adb.show();
            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeAutomobiliste.this);
                adb.setTitle("Info");
                adb.setMessage("Suppression pas terminer ");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }

    }

    private class AppelerAuto extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeAutomobiliste.this,
                    "Progress", "waiting please ...", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO Auto-generated method stub

            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL2, NumeroCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);

            js.initialisation(NumeroCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);
            try {
                is = js.Connect(strURL2);
            } catch (Exception e) {
                Toast.makeText(ListeAutomobiliste.this,
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
                Toast.makeText(ListeAutomobiliste.this,
                        "Erreyr Analyse de donnée ", Toast.LENGTH_LONG).show();
            }

            return ar;

        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (result == null) {
                Toast.makeText(ListeAutomobiliste.this,
                        "Erreur Analyse de donnée", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    nt = json_data.getString("NTel");
                    Intent ci = new Intent(Intent.ACTION_CALL);
                    ci.setData(Uri.parse("tel:" + nt + ""));
                    startActivity(ci);
                }

            } catch (JSONException e) {
                Log.e("log_tag", "Erreur Analyse de donnée" + e.toString());
            }
        }

    }

    private class LocaliserAuto extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeAutomobiliste.this,
                    "Progress", "waiting please..", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            // TODO Auto-generated method stub
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL2, NumeroCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);

            js.initialisation(NumeroCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);

            try {
                is = js.Connect(strURL2);

            } catch (Exception e) {
                Toast.makeText(ListeAutomobiliste.this,
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
                Toast.makeText(ListeAutomobiliste.this,
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
                Toast.makeText(ListeAutomobiliste.this,
                        "Erreur Analyse de donnée", Toast.LENGTH_LONG).show();
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    String lt = json_data.getString("Latitude");
                    String lg = json_data.getString("Longitude");

                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri
                            .parse("http://maps.google.com/maps?saddr=35.671127, 10.101017&daddr="
                                    + lt + "," + lg + ""));
                    startActivity(it);
                }

            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data" + e.toString());
            }
        }

    }

}
