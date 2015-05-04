package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class Depanneur extends Activity {
    Button consulter, appeler;
    String NumeroCinDepanneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depanneur);
        consulter = (Button) findViewById(R.id.button1);
        appeler = (Button) findViewById(R.id.button3);
        Intent data = this.getIntent();
        NumeroCinDepanneur = data.getExtras().getString("CinDepanneur");
        consulter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Depanneur.this, ListeDemmande.class);
                i.putExtra("CinDepanneur", NumeroCinDepanneur);
                startActivity(i);
            }
        });
        appeler.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Depanneur.this, ListeAutomobiliste.class);
                i.putExtra("Action", "Appeler");
                startActivity(i);
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

}
