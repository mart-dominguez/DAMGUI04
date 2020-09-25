package isi.dam.damgui04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_CICLO_VIDA_APP ="EJEMPLO_CICLO_VIDA";
    Button btnIrA2;
    EditText edTexto1;
    TextView textView1;
    Button botonX;
    FloatingActionButton floatingActionButton;
    Toolbar miToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onCreate: Activity 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miToolbar = findViewById(R.id.toolbar);
        btnIrA2 = findViewById(R.id.btnNavegarAct2);
        edTexto1 = findViewById(R.id.texto1);
        textView1 = findViewById(R.id.textView1);
        botonX = findViewById(R.id.botonX);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        setSupportActionBar(miToolbar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MainActivity2.class); // intent explicito
                startActivity(i);
            }
        });
        btnIrA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botonX.isEnabled()){
                    botonX.setEnabled(false);
                    textView1.setTextColor(Color.RED);
                    edTexto1.setVisibility(View.INVISIBLE);
                } else {
                    botonX.setEnabled(true);
                    textView1.setTextColor(Color.BLUE);
                    edTexto1.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcion1:
                Toast.makeText(this,"CLICK EN OPCION 1",Toast.LENGTH_LONG).show();
                break;
            case R.id.opcion2:
                Toast.makeText(this,"CLICK EN OPCION 2",Toast.LENGTH_LONG).show();
                break;
            case R.id.opcion3:
                Toast.makeText(this,"CLICK EN OPCION 3",Toast.LENGTH_LONG).show();
                break;
            case R.id.opcion4:
                Toast.makeText(this,"CLICK EN OPCION 4",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onSaveInstanceState: Activity 1");
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onSaveInstanceState: COLOR: "+textView1.getCurrentTextColor());
        outState.putInt("COLOR",textView1.getCurrentTextColor());
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onSaveInstanceState: getVisibility: "+edTexto1.getVisibility());
        outState.putBoolean("MOSTRAR_EDT1",edTexto1.getVisibility()==View.VISIBLE);
        outState.putInt("MOSTRAR_EDT1_INT",edTexto1.getVisibility());
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onSaveInstanceState: isEnabled: "+botonX.isEnabled());
        outState.putBoolean("MOSTRAR_BTN_X",botonX.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onRestoreInstanceState: Activity 1");
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onRestoreInstanceState: COLOR"+savedInstanceState.getInt("COLOR"));
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onRestoreInstanceState: getVisibility: "+savedInstanceState.getInt("MOSTRAR_EDT1_INT"));
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onRestoreInstanceState: MOSTRAR_BTN_X: "+savedInstanceState.getBoolean("MOSTRAR_BTN_X"));

        textView1.setTextColor(savedInstanceState.getInt("COLOR"));
        edTexto1.setVisibility(savedInstanceState.getInt("MOSTRAR_EDT1_INT"));
        botonX.setEnabled(savedInstanceState.getBoolean("MOSTRAR_BTN_X"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onStart: Activity 1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onResume: Activity 1");
        // registrarme
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onPause: Activity 1");
        // desregistrarme
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onStop: Activity 1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onDestroy: Activity 1");
    }
}