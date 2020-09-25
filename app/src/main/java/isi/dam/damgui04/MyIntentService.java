package isi.dam.damgui04;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_PROMEDIO = "isi.dam.damgui04.action.FOO";
    public static final String ACTION_SUMA = "isi.dam.damgui04.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "isi.dam.damgui04.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "isi.dam.damgui04.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        miHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast.makeText(MyIntentService.this," Promedio total es "+ ( msg.obj) + ") ",Toast.LENGTH_LONG).show();
            }
        };
        Log.d("MyIntentService", "onCreate: ");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("MyIntentService", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("MyIntentService", "onDestroy: ");
        super.onDestroy();
    }

    Handler miHandler;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, Double[] param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_PROMEDIO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, Double[] param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_SUMA);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MyIntentService", "onHandleIntent: ");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROMEDIO.equals(action)) {
                Log.d("MyIntentService", "onHandleIntent: CALCULAR PROMEDIO ");

                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final double[] param2 = intent.getDoubleArrayExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_SUMA.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final double[] param2 = intent.getDoubleArrayExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, double[] param2) {
        double sumaTotal = 0.0;
        for(int i=0;i<param2.length;i++){
            sumaTotal += param2[i];
            try {
                Thread.sleep(750);
                Log.d("MyIntentService", "se llama a handleActionFoo la actividad: "+param2[i]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // lanzar notificacion
        Message m = miHandler.obtainMessage();
        m.obj = "El promedio es "+(sumaTotal/param2.length);
        miHandler.sendMessage(m);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, double[] param2) {
        double sumaTotal = 0.0;
        for(int i=0;i<param2.length;i++){
            sumaTotal += param2[i];
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("MyIntentService", "se llama a handleActionBaz la actividad: "+param2[i]);

        }
        // lanzar notificacion
        Toast.makeText(MyIntentService.this," Suma total es "+ ( sumaTotal) + ") ",Toast.LENGTH_LONG).show();
    }
}
