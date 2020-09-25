package isi.dam.damgui04;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MiIntentService2 extends IntentService {

    Handler miHandler;

    public MiIntentService2() {
        super("MiIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        miHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Toast.makeText(MiIntentService2.this,"TERMINO EL SERVICIO con "+msg.arg1,Toast.LENGTH_LONG).show();
                // mandar una notificaion
            }
        };
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // se ejecuta en un hilo secundario
        int param = intent.getIntExtra("MAX",10);
        int suma = 0;
        for(int i=0;i<param;i++){
            suma += i;
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        miHandler.sendMessage(miHandler.obtainMessage(1,suma,1));
    }
}
