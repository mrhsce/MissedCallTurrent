package mct.androtech.mrhsce.missedcallturrent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by mrhs on 9/1/15.
 */
public class Caller {
    private final Boolean LOCAL_SHOW_LOG = true;
    private final Integer DELAY_TIME = 100;

    public Boolean makeCall(final Context ctx, String pnoneNum){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + pnoneNum));
        ctx.startActivity(callIntent);


        // This runnable is used to return the main activity to the front after
        // showing the dialer activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                log("The handler has been executed");
                Intent i=new Intent(ctx,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ctx.startActivity(i);
            }
        }, DELAY_TIME);

        return true;
    }

    private void log(String message){
        if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
            Log.d(this.getClass().getSimpleName(), message);
    }
}
