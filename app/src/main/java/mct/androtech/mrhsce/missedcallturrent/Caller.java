package mct.androtech.mrhsce.missedcallturrent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by mrhs on 9/1/15.
 */
public class Caller {

    public Boolean makeCall(Context ctx, String pnoneNum){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + pnoneNum));
        ctx.startActivity(callIntent);

        return true;
    }
}
