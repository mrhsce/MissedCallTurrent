package mct.androtech.mrhsce.mct;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mct.androtech.mrhsce.mct.R;

public class AttackExecutionActivity extends Activity {

    private final Boolean LOCAL_SHOW_LOG = true;

    MissedCallAttack mcaObject; // This is used to hold the information of the mc procedure
    Caller caller;   // This is used to execute each single call
    Integer doneCalls = 0;
    Integer phoneNumPointer = 0;  // This is used when several phone nums are inserted
    Button pauseButton,stopButton;
    TextView phoneNumTextView,statusTextView,progressTextView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_execution);

        // Loading the contents of the mca from the intent
        mcaObject = new MissedCallAttack(getIntent());
        log("The mca object has been loaded from the intent");

        caller = new Caller(this);

        setupGUI();

        // Starting the first call
        caller.makeCall(mcaObject.phoneNumList.get(0),mcaObject.callHold);
    }


    public void nextCall(){
        phoneNumPointer ++;
            if(phoneNumPointer == mcaObject.phoneNumList.size()){
                phoneNumPointer = 0;
                doneCalls ++;
                if(doneCalls < mcaObject.callRepeat){
                    if(mcaObject.interval == 0){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                caller.makeCall(mcaObject.phoneNumList.get(phoneNumPointer),mcaObject.callHold);
                            }
                        },2000);
                    }
                    else{
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                caller.makeCall(mcaObject.phoneNumList.get(phoneNumPointer),mcaObject.callHold);
                            }
                        },mcaObject.interval*1000);
                    }
                }
            }
    }

    private void setupGUI(){
        //Declaring the views
        pauseButton = (Button)findViewById(R.id.pauseButton);
        stopButton = (Button)findViewById(R.id.stopButton);
        phoneNumTextView = (TextView)findViewById(R.id.phoneNumberTextView);
        statusTextView = (TextView)findViewById(R.id.statusTextView);
        progressTextView = (TextView)findViewById(R.id.progressTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initializing the views

    }

    private void log(String message){
        if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
            Log.d(this.getClass().getSimpleName(), message);
    }

}
