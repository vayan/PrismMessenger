package pm.PrismMessenger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

/**
 * Created by vayan on 2/8/15.
 */
public class Notify {
    private Context mcontext;
    
    public Notify(Context mcontext) {
        this.mcontext = mcontext;
    }
    
    public int NewMessage(String content) {
        Notification.Builder mBuilder =
                new Notification.Builder(mcontext)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Received Encrypted Text")
                        .setContentText("Open App to See");
        Intent resultIntent = new Intent(mcontext, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mcontext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
        return 0;
    }
    
}
