package neo.vn.test365children.Service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import neo.vn.test365children.Models.MessageEvent;

public class ServiceDownTime extends Service {
    public static final int TOTAL_TIME = 30 * 60 * 1000;
    CountDownTimer Timer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         Timer = new CountDownTimer(TOTAL_TIME, 1000) {
            public void onTick(long millisUntilFinished) {
                EventBus.getDefault().post(new MessageEvent("Service", 0, millisUntilFinished));
            }

            public void onFinish() {
                EventBus.getDefault().post(new MessageEvent("Service", 1, 0));
            }
        }.start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Timer.cancel();
        super.onDestroy();
    }
}