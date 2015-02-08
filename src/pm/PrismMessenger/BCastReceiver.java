package pm.PrismMessenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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

    }
}
