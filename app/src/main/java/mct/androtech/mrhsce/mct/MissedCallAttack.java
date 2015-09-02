package mct.androtech.mrhsce.mct;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mrhs on 9/2/15.
 */

// This class only holds the information about the missed call operation and does no data manipulation
public class MissedCallAttack {
    private final Boolean LOCAL_SHOW_LOG = true;

    public final ArrayList<String> phoneNumList;
    public final Integer callRepeat,callHold,interval;


    public MissedCallAttack(ArrayList<String> list, Integer repeat, Integer hold, Integer intrvl){
        phoneNumList = list;
        callHold = hold;
        callRepeat = repeat;
        interval = intrvl;
    }

    private void log(String message){
        if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
            Log.d(this.getClass().getSimpleName(), message);
    }
}
