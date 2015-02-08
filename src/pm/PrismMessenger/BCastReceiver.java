package pm.PrismMessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by vayan on 2/8/15.
 */

public class BCastReceiver extends BroadcastReceiver {
    private static final String TAG = "Broadcast";
    private Context mcontext;
    
    public BCastReceiver(Context mcontext) {
        this.mcontext = mcontext;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Receive broadcast");
        Notify nf = new Notify(mcontext);
        
        nf.NewMessage("salut!");
    }
}
