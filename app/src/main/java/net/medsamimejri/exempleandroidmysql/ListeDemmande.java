package net.medsamimejri.exempleandroidmysql;

import android.app.AlertDialog;
import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class ListeDemmande extends ListActivity {
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/SelectDemmande.php";

    String ca, d, NumeroCinDepanneur, NumeroCinAutomobiliste;
    InputStream is = null;
    JSONArray ar;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_demmande);
        Intent data = this.getIntent();
        NumeroCinDepanneur = data.getExtras().getString("CinDepanneur");
        new SelectDemmande().execute();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selection = l.getItemAtPosition(position).toString();
        NumeroCinAutomobiliste = "";
        for (int s = 35; s < 43; s++) {

            NumeroCinAutomobiliste += selection.charAt(s);

        }
        Intent i = new Intent(ListeDemmande.this, EnvoyerReponse.class);
        i.putExtra("CinDepanneur", NumeroCinDepanneur);
        i.putExtra("CinAutomobiliste", NumeroCinAutomobiliste);
        startActivity(i);

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

    private class SelectDemmande extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeDemmande.this,
                    "Progress", "Waiting Please", false, false);
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
                Toast.makeText(ListeDemmande.this, "Error in http connection ",
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
                Toast.makeText(ListeDemmande.this, "Erreur Analyse de donnée",
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
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeDemmande.this);
                adb.setTitle("Info");
                adb.setMessage("Aucune Demmande disponible");
                adb.setPositiveButton("Ok", null);
                adb.show();
                return;
            }
            String[] Demmande = CreateArray(result.length());
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    ca = json_data.getString("CinAutomobiliste");
                    d = json_data.getString("Demmande");

                    Demmande[i] = "Demmande N° : " + (i + 1)
                            + " Cin Automobiliste :" + ca;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListeDemmande.this,
                        android.R.layout.simple_list_item_1, Demmande);
                setListAdapter(adapter);

            } catch (JSONException e) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeDemmande.this);
                adb.setTitle("Info");
                adb.setMessage("Aucune Demmande disponible");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }
    }

}
