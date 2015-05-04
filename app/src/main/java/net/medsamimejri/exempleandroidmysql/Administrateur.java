package net.medsamimejri.exempleandroidmysql;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;


public class Administrateur extends Activity {

    Button gererDepanneur, gererAutomobiliste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrateur);
        gererDepanneur = (Button) findViewById(R.id.button1);
        gererAutomobiliste = (Button) findViewById(R.id.button2);

        gererDepanneur.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(Administrateur.this,GererDepanneur.class);
                startActivity(i);
            }
        });

        gererAutomobiliste.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(Administrateur.this,GererAutomobiliste.class);
                startActivity(i);
            }
        });

    }

}
