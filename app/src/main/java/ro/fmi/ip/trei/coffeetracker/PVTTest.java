package ro.fmi.ip.trei.coffeetracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PVTTest extends AppCompatActivity {
    public static final String KEY_MEAN_SCORE = "score";

    private static class MyHandler extends Handler {}
    private final MyHandler mHandler = new MyHandler();
    public static class MyRunnable implements Runnable {
        private final WeakReference<Activity> mActivity;

        public MyRunnable(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            Activity activity = mActivity.get();
            if (activity != null) {

            }
        }
    }

    public static class WaitForDot extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            if(testPicat) {
                cron = new MyChronometer(context);
                imagine.get().setVisibility(View.VISIBLE);
                cron.start();
                this.ecran.get().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopCron();
                        if (numarTeste.getNumarTeste() < 2 ) {
                            restart.get().setText("Urmatorul test.");
                        } else {
                            restart.get().setText("Inchide.");
                        }
                        restart.get().setEnabled(true);
                    }
                });
            }

        }

        WeakReference<View> imagine;
        WeakReference<TextView> text;
        WeakReference<View> ecran;
        WeakReference<Button> restart;
        WeakReference<ArrayList<Double>> scoreKeeper;
        private Context context;
        private MyChronometer cron;
        private boolean testPicat;
        private NumaratorTeste numarTeste;


        public void stopCron(){
            if(cron != null) {
                cron.stop();
                Double score = new Double(cron.msElapsed);
                this.scoreKeeper.get().add(score);
                double sumaScoruri = 0;
                for (Double scor : scoreKeeper.get()) {
                    sumaScoruri += scor ;
                }
                //(double) cron.msElapsed / 1000
                int verde = 255;
                int rosu = 0;
                verde = (Math.abs(score - 350) <= 255) ? 255 - (int)Math.abs(score - 350) : 0;
                rosu = (Math.abs(score - 350) <= 255) ? (int)Math.abs(score - 350) : 255;
                this.text.get().setTextColor(Color.rgb(rosu, verde, 0));
                //this.text.get().setText(String.format("%.4f" ,sumaScoruri / scoreKeeper.get().size() / 1000) + " secunde.");
                this.text.get().setText(String.format("%.4f" ,score /  1000) + " secunde.");


            }
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            try {
                testPicat = true;
                ecran.get().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testPicat = false;
                        text.get().setTextColor(Color.WHITE);
                        text.get().setText("Trebuie apasat dupa ce apare bulina.");
                        restart.get().setText("RESTART !!!");
                        restart.get().setEnabled(true);
                    }
                });
                if(testPicat) {
                    Thread.sleep(integers[0]);
                }
            }
            catch (Exception e){
            }
            return null;
        }

        public WaitForDot(View ecran, TextView text, View imagine, Button restart, NumaratorTeste numarTeste, ArrayList<Double> scoreKeeper, Context context) {
            this.ecran = new WeakReference<>(ecran);
            this.text = new WeakReference<>(text);
            this.imagine = new WeakReference<>(imagine);
            this.restart = new WeakReference<>(restart);
            this.scoreKeeper = new WeakReference<>(scoreKeeper);
            this.context = context;
            this.numarTeste = numarTeste;


        }
    }

    private final int numarMaximDeSecunde = 5000;
    private final int numarMinimDeSecunde = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvttest);
        final ImageView imagine = findViewById(R.id.yellow_dot);
        final TextView text = findViewById(R.id.text);
        final View ecran = findViewById(R.id.ecran);
        final Button restart = findViewById(R.id.restart);
        final NumaratorTeste numaratorTeste = new NumaratorTeste();
        final ArrayList<Double> scoreKeeper = new ArrayList<>();
        text.setText("Testul 1/3.");


        Random r = new Random();
        final int milis = r.nextInt(numarMaximDeSecunde - numarMinimDeSecunde) + numarMinimDeSecunde;
        WaitForDot wFD = new WaitForDot(ecran, text, imagine, restart, numaratorTeste, scoreKeeper, getApplicationContext());
        restart.setEnabled(false);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagine.setVisibility(View.INVISIBLE);
                text.setTextColor(Color.WHITE);
                text.setText("Testul " + (scoreKeeper.size() + 1) + "/3.");
                restart.setEnabled(false);
                if (restart.getText().equals("Urmatorul test.")){
                    numaratorTeste.setNumarTeste(numaratorTeste.getNumarTeste() + 1);


                }
                if (restart.getText().equals("Inchide.") && scoreKeeper.size() == 3) {
                    text.setText("");
                    Intent resultData = new Intent();
                    double meanScore = 0;
                    for (double score : scoreKeeper) {
                        meanScore += score;
                    }
                    resultData.putExtra(KEY_MEAN_SCORE, meanScore / 3);
                    setResult(2, resultData);
                    finish();
                }
                WaitForDot newForDotWaiter = new WaitForDot(ecran, text , imagine, restart, numaratorTeste, scoreKeeper, getApplicationContext());
                newForDotWaiter.execute(milis);
            }
        });
        wFD.execute(milis);

    }





}
