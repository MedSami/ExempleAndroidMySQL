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


public class ListeReponse extends ListActivity {
    public static final String strURL = "http://10.0.2.2:8080/Tunisie_Telecom/SelectReponse.php";
    ListView maList;
    InputStream is = null;
    JSONArray ar;
    String NumeroCinDepanneur, NumCinAutomobiliste, result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_reponse);
        Intent data = this.getIntent();
        NumCinAutomobiliste = data.getExtras().getString("CinAutomobiliste");

        new SelectReponse().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String selection = l.getItemAtPosition(position).toString();

        NumeroCinDepanneur = "";
        for (int s = 31; s < 39; s++) {

            NumeroCinDepanneur += selection.charAt(s);

        }
        Intent i = new Intent(ListeReponse.this, Reponse.class);
        i.putExtra("CinDepanneur", NumeroCinDepanneur);
        i.putExtra("CinAutomobiliste", NumCinAutomobiliste);
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

    private class SelectReponse extends AsyncTask<Void, Void, JSONArray> {
        ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(ListeReponse.this,
                    "Progress", "Waiting Please..", false, false);
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            JavaScriptOrientedNotation js = new JavaScriptOrientedNotation(
                    strURL, NumCinAutomobiliste, null, null, null, null, null,
                    null, null, null, null);
            js.initialisation(NumCinAutomobiliste, null, null, null, null,
                    null, null, null, null, null);
            try {
                is = js.Connect(strURL);
            } catch (Exception e) {
                Toast.makeText(ListeReponse.this, "Error in http connection ",
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
                Toast.makeText(ListeReponse.this,
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
            if(result==null){
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeReponse.this);
                adb.setTitle("Info");
                adb.setMessage("Aucune Réponse disponible");
                adb.setPositiveButton("Ok", null);
                adb.show();
                return;
            }
            try
            {
                String Reponse[] = CreateArray(result.length());
                for (int i = 0; i < result.length(); i++) {
                    JSONObject json_data = result.getJSONObject(i);
                    String c = json_data.getString("CinDepanneur");
                    Reponse[i] = "Reponse N° : " + (i + 1)
                            + " Cin Depanneur : " + c;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ListeReponse.this, android.R.layout.simple_list_item_1,
                        Reponse);
                setListAdapter(adapter);

            } catch (JSONException e) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ListeReponse.this);
                adb.setTitle("Info");
                adb.setMessage("Aucune Réponse disponible");
                adb.setPositiveButton("Ok", null);
                adb.show();
            }
        }
    }
}

