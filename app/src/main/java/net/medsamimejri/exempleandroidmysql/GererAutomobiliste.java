package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;



public class GererAutomobiliste extends Activity {
    Button Ajouter, Modifier, Supprimer, Localiser, Appeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_automobiliste);
        Ajouter = (Button) findViewById(R.id.button1);
        Modifier = (Button) findViewById(R.id.button2);
        Supprimer = (Button) findViewById(R.id.button3);
        Localiser = (Button) findViewById(R.id.button4);
        Appeler = (Button) findViewById(R.id.button5);

        Ajouter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(GererAutomobiliste.this,AjouterAutomobiliste.class);
                i.putExtra("Action", "Modifier");
                startActivity(i);
            }
        });
        Modifier.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(GererAutomobiliste.this,ListeAutomobiliste.class);
                i.putExtra("Action", "Modifier");
                startActivity(i);
            }
        });

        Supprimer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(GererAutomobiliste.this,ListeAutomobiliste.class);
                i.putExtra("Action", "Supprimer");
                startActivity(i);
            }
        });

        Localiser.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(GererAutomobiliste.this,ListeAutomobiliste.class);
                i.putExtra("Action", "Localiser");
                startActivity(i);
            }
        });

        Appeler.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(GererAutomobiliste.this,ListeAutomobiliste.class);
                i.putExtra("Action", "Appeler");
                startActivity(i);
            }
        });


    }
}
