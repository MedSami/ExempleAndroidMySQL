package net.medsamimejri.exempleandroidmysql;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    Button depanneur, administrateur, automobiliste;
    String Type_utilistaeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        administrateur = (Button) findViewById(R.id.administrateur);
        depanneur = (Button) findViewById(R.id.depanneur);
        automobiliste = (Button) findViewById(R.id.automobiliste);

        administrateur.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, Login.class);
                //	Type_utilistaeur="Administrateur";
                i.putExtra("Type", "Administrateur");

                startActivity(i);
            }
        });

        depanneur.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, Login.class);
                // Type_utilistaeur="Depanneur";
                i.putExtra("Type", "Depanneur");

                startActivity(i);
            }
        });

        automobiliste.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, Login.class);
//				 Type_utilistaeur="Automobiliste";
                i.putExtra("Type", "Automobiliste");

                startActivity(i);
            }
        });
    }

}
