package neo.vn.test365children.Service;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.util.ArrayMap;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import neo.vn.test365children.Activity.ActivityLogin;
import neo.vn.test365children.Activity.ActivityHome;
import neo.vn.test365children.R;

/**
 * Created by QQ on 8/29/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static int ONGOING_NOTIFICATION_ID = 76;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            hienThiThongBao(remoteMessage.getFrom(), remoteMessage.getMessageType());
        } else {
            Map<String, String> mMap = new ArrayMap<>();
            mMap = remoteMessage.getData();
            hienThiThongBao(mMap);

        }

    }

    private void hienThiThongBao(Map<String, String> mMap) {
        Intent intent = null;
        String type = mMap.get("type");
        String id = mMap.get("id");
        String content = mMap.get("content");
        String flighttype = mMap.get("flighttype");
        String flightdate  = mMap.get("flightdate");
        if (type.equals("1")) {
            intent = new Intent(this, ActivityHome.class);
        } else {
           // intent = new Intent(this, MainActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("NoibaiAirport")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(""))
                .setContentText(content)
                /* .setSound(Uri.parse("android.resource://"
                         + getApplicationContext().getPackageName() + "/"
                         + R.raw.sound_notification))*/
                .setContentIntent(pendingIntent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(100, builder.build());
    }

    private void hienThiThongBao(String title, String body) {
        int number_notifycation = 0;
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(title))


               /* .setSound(Uri.parse("android.resource://"
                        + getApplicationContext().getPackageName() + "/"
                        + R.raw.notifi))*/
                .setNumber(++number_notifycation)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        //    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(100, builder.build());
    }
}
