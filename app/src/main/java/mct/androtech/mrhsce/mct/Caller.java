package mct.androtech.mrhsce.mct;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;


import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by mrhs on 9/1/15.
 */
public class Caller {
    private final Boolean LOCAL_SHOW_LOG = true;
    private final Integer DELAY_TIME = 100;
    private AttackExecutionActivity ctx;
    private Method endCallMethod; //Used for stopping the call
    private Object telephonyService;  //Used for stopping the call
    public Boolean IS_CALLING = false; // This var shows the condition of the caller
    TelephonyManager tm;   // This is used to get the call status


    public Caller(final AttackExecutionActivity ctx){
        this.ctx = ctx;

        //Initialize telephonyService
        try {
            log("Caller initialization started");
            tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName(tm.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = m.invoke(tm);
            Class<?> telephonyServiceClass = Class.forName(telephonyService.getClass().getName());
            endCallMethod = telephonyServiceClass.getDeclaredMethod("endCall");
            log("Caller has been successfully initialized");
        }catch (Exception e){
            e.printStackTrace();
            log("Problem initializing the telephony service");
        }
    }

    public Boolean makeCall( String pnoneNum,Integer holdTime){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + pnoneNum));
        ctx.startActivity(callIntent);
        IS_CALLING = true;
        log("The call has been started");



        // This runnable is used to return the main activity to the front after
        // showing the dialer activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                log("The calling screen brought to background");
                // This part looks for this task in running tasks and brings it to front
                final ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
                final List<ActivityManager.RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
                for (int i = 0; i < recentTasks.size(); i++) {
                    if (recentTasks.get(i).baseActivity.toShortString().indexOf(ctx.getPackageName()) > -1) {
                        activityManager.moveTaskToFront(recentTasks.get(i).id, ActivityManager.MOVE_TASK_WITH_HOME);
                    }
                }
            }
        }, DELAY_TIME);


        // This runnable is used to end the call after the desired time
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (IS_CALLING) {
                        log("The call time limit has been reached");
                        endCallMethod.invoke(telephonyService);  // This line stops the call
                        handler.removeCallbacksAndMessages(null);
                        nextCall();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log("Problem ending the call inside runnable");
                }
                log("The call ending runnable executed");
            }
        }, holdTime * 1000);

        //This runnable every second checks for the holding of the call
        handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               if(IS_CALLING){
                   if(tm.getCallState() != TelephonyManager.CALL_STATE_IDLE){
                       log("Call is holding for next one sec");
                       new Handler().postDelayed(this,1000);
                   }
                   else{
                       log("The call has end before time limit");
                       handler.removeCallbacksAndMessages(null);
                       nextCall();
                   }
               }
           }
       },1000);

        return true;
    }

    private void nextCall(){ // This function tells the AttackExecutionActivity about the ending of the single call
        log("Going to the next call");
        IS_CALLING = false;
        ctx.nextCall();

    }

    private void log(String message){
        if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
            Log.d(this.getClass().getSimpleName(), message);
    }
}
