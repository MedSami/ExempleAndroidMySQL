package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;

import android.view.View;
import android.widget.Button;


public class Automobiliste extends Activity {
    Button demmande,consulter,appeler;
    String NumCinAutomobiliste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobiliste);
        Intent data = this.getIntent();
        NumCinAutomobiliste = data.getExtras().getString("CinAutomobiliste");
        demmande=(Button)findViewById(R.id.button1);
        consulter=(Button)findViewById(R.id.button2);
        appeler=(Button)findViewById(R.id.button3);


        demmande.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(Automobiliste.this,ListeDepanneur.class);
                i.putExtra("Action", "Demmande");
                i.putExtra("CinAutomobiliste", NumCinAutomobiliste);
                startActivity(i);
            }
        });

        consulter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(Automobiliste.this,ListeReponse.class);
                i.putExtra("CinAutomobiliste", NumCinAutomobiliste);
                startActivity(i);
            }
        });
        appeler.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(Automobiliste.this,ListeDepanneur.class);
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
