package mct.androtech.mrhsce.mct;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mct.androtech.mrhsce.mct.R;

public class AttackExecutionActivity extends Activity {

    private final Boolean LOCAL_SHOW_LOG = true;

    private final Integer STATUS_WAITING = 0;
    private final Integer STATUS_CALLING = 1;
    private final Integer STATUS_PAUSED = 2;
    private final Integer STATUS_FINISH = 3;

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
        setCallStatus(STATUS_CALLING);
    }


    public void nextCall(){
        setCallStatus(STATUS_WAITING);
        phoneNumPointer ++;
            if(phoneNumPointer == mcaObject.phoneNumList.size()){
                phoneNumPointer = 0;
                doneCalls ++;
                if(doneCalls < mcaObject.callRepeat){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            caller.makeCall(mcaObject.phoneNumList.get(phoneNumPointer),mcaObject.callHold);
                            setCallStatus(STATUS_CALLING);
                        }
                    },mcaObject.interval*1000);
                }
                else{
                    setCallStatus(STATUS_FINISH);
                }
            }
        updateGUI();
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

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO here a procedure to pause the process and change the
                // pause button to resume button
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO here a procedure to finish the process
            }
        });

        phoneNumTextView.setText(mcaObject.phoneNumList.get(0));
        setCallStatus(STATUS_WAITING);
        progressBar.setMax(mcaObject.callRepeat);
        progressBar.setProgress(0);
        progressTextView.setText(doneCalls+"    از    "+mcaObject.callRepeat);

    }

    private void updateGUI(){  //After each call this func updates the views accordingly
        phoneNumTextView.setText(mcaObject.phoneNumList.get(phoneNumPointer));
        progressBar.setProgress(doneCalls);
        progressTextView.setText(doneCalls+"    از    "+mcaObject.callRepeat);
    }

    private void setCallStatus(Integer status){
        switch (status){
            case 1:
                statusTextView.setText("Calling...");
                break;
            case 0 :
                statusTextView.setText("Waiting...");
                break;
            case 2 :
                statusTextView.setText("Paused");
                break;
            case 3 :
                statusTextView.setText("Finished!");
                break;
        }
    }

    private void log(String message){
        if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
            Log.d(this.getClass().getSimpleName(), message);
    }

}
