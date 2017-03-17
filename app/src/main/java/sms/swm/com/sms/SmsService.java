package sms.swm.com.sms;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SmsService extends Service {
    public class LocalBinder extends Binder {
        public SmsService getService() {
            return SmsService.this;
        }
    }

    public IBinder binder = new LocalBinder();

    public SmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void send(String phone, String text) {
        SmsHelper.send(this, phone, text);
    }
}
