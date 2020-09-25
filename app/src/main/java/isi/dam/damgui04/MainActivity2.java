package isi.dam.damgui04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {


    Button btn1, btn2,btn3,btn4,btn5,btn6,btn7;
    TextView resultado;
    ProgressBar barraProgreso;
    Handler miHandler;
    CalcularPromedioDoblesTask tarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onCreate: Activity 2");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        resultado = findViewById(R.id.resultado);
        barraProgreso = findViewById(R.id.progressBar);
        tarea = new CalcularPromedioDoblesTask();

        miHandler = new  Handler(Looper.getMainLooper())  {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d(MainActivity.TAG_CICLO_VIDA_APP, "Mensaje llego - what"+msg.what);
                Log.d(MainActivity.TAG_CICLO_VIDA_APP, "Mensaje llego - arg1"+msg.arg1);
                Log.d(MainActivity.TAG_CICLO_VIDA_APP, "Mensaje llego - arg2"+msg.arg2);
                Log.d(MainActivity.TAG_CICLO_VIDA_APP, "Mensaje llego - obj"+msg.obj);
                Log.d(MainActivity.TAG_CICLO_VIDA_APP, "Mensaje llego - replyTo"+msg.replyTo);
                Log.d(MainActivity.TAG_CICLO_VIDA_APP, "Mensaje llego - sendingUid"+msg.sendingUid);
                switch (msg.what){
                    case 1:
                        resultado.setText("Inicia boton 1 / "+System.currentTimeMillis());
                        break;
                    case 2:
                        resultado.setText("Termina boton 1 / "+System.currentTimeMillis());
                        break;
                    case 3:
                        resultado.setText("Actaulizacion parcial. Procesando  "+msg.arg1 + " suma parcial "+msg.arg2);
                        break;
                }
            }
        };


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procesoLargo();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        // ENVIO UN MSG PARA ACTUALIZAR LA iNTERFACE
                        final Message miMsg1 = new Message();
                        miMsg1.what = 1;
                        miHandler.sendMessage(miMsg1);
                        // COMIENZA EL PROCESO LARGO
                        procesoLargo();
                        // ENVIO MENSAJE DESDE EL HILO SECUNDARIO PARA AVISAR QUE TERMINO
                        final Message miMsg2 = new Message();
                        miMsg2.what = 2;
                        miHandler.sendMessage(miMsg2);
                    }
                };
                Thread t1 = new Thread(r);
                t1.start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this,"Inicia boton 2",Toast.LENGTH_LONG).show();
                resultado.setText("Inicia boton 2 / "+System.currentTimeMillis());
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        procesoLargo();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultado.setText("Termina boton 2 / "+System.currentTimeMillis());
                                Toast.makeText(MainActivity2.this,"TErmina boton 2",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                };
                Thread t1 = new Thread(r);
                t1.start();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Double> listaD = new ArrayList<>();
                Random r = new Random();
                for(int i =0;i<250;i++){
                    listaD.add(r.nextDouble()*10000);
                }
                tarea.execute(listaD.toArray(new Double[0]));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tarea.getStatus().equals(AsyncTask.Status.RUNNING)){
                    tarea.cancel(true);
                } else {
                    Toast.makeText(MainActivity2.this,"NO SE ESTA RUNNING",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] misDobles=  { 1.0,434.5,324.2,54.2,68.3,563.1,34.54};
                Intent intentServicio1 = new Intent(MainActivity2.this,MyIntentService.class);
                intentServicio1.setAction(MyIntentService.ACTION_PROMEDIO);
                intentServicio1.putExtra(MyIntentService.EXTRA_PARAM1,"HOLA");
                intentServicio1.putExtra(MyIntentService.EXTRA_PARAM2,misDobles);
                Log.d("MyIntentService", "se llama a que inicie el servicio: ");

                startService(intentServicio1);
                Log.d("MyIntentService", "se llama a FINISH la actividad: ");
                finish();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] misDobles=  { 1.0,434.5,324.2,54.2,68.3,563.1,34.54};
                Intent intentServicio2 = new Intent(MainActivity2.this,MiIntentService2.class);
                intentServicio2.putExtra("MAX",misDobles);
                Log.d("MyIntentService", "se llama a que inicie el servicio 2: ");

                startService(intentServicio2);
                Log.d("MyIntentService", "se llama a FINISH la actividad 2: ");
                finish();
            }
        });
    }

    private void procesoLargo(){
        try {
            List<Integer> lista = new ArrayList<>();
            for(int i = 0;i<1000;i++){
                lista.add(i+1);
            }
            Thread.sleep(2000);
            Integer suma = 0;
            for(Integer i: lista){
                suma += i;
                if(i%50 ==0 ){
                    final Message miMsg1 = new Message();
                    miMsg1.what = 3;
                    miMsg1.arg1 = i;
                    miMsg1.arg2 = suma;
                    miHandler.sendMessage(miMsg1);
                    Thread.sleep(400);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onStart: Activity 2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onResume: Activity 2");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onPause: Activity 2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onStop: Activity 2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.TAG_CICLO_VIDA_APP, "onDestroy: Activity 2");
    }

    class CalcularPromedioDoblesTask extends AsyncTask<Double,Integer,String>{

        @Override
        protected void onPreExecute() {
            resultado.setText("Antes de calcular el promedios ASYNC");
        }

        @Override
        protected String doInBackground(Double... doubles) {
            int cantidad = 0;
            double total = 0;
            for(Double d : doubles){
                if(isCancelled()) return "cancelado";
                cantidad++;
                total += d;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(100* (cantidad/doubles.length), cantidad);
            }
            return "El promedio es : ("+(total/cantidad)+")";
        }

        @Override
        protected void onPostExecute(String s) {
            resultado.setText(s);
            resultado.setTextColor(Color.RED);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            barraProgreso.setProgress(values[0]);
            resultado.setText("Procesando async "+values[1]);
        }

        @Override
        protected void onCancelled(String s) {
            resultado.setText(s);
            resultado.setTextColor(Color.BLUE);
        }
    }

}