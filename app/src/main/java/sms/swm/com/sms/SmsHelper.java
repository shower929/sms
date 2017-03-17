package sms.swm.com.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import static sms.swm.com.sms.SmsReceiver.SMS_PHONE;
import static sms.swm.com.sms.SmsReceiver.SMS_SEND_STATUS;
import static sms.swm.com.sms.SmsReceiver.SMS_DELIVER_REPORT;

/**
 * Created by yangzhenyu on 2017/3/17.
 */

public class SmsHelper {
    public static void send(Context context, String phone, String text) {
        PendingIntent sentResult = getSendResultPendingIntent(context, phone);
        PendingIntent deliverStatus = getDeliverStatusPendingIntent(context, phone);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, text, sentResult, deliverStatus);
    }

    private static PendingIntent getDeliverStatusPendingIntent(Context context, String phone) {
        Intent smsIntent = new Intent(SMS_DELIVER_REPORT).setClass(context, SmsReceiver.class);
        smsIntent.putExtra(SMS_PHONE, phone);
        PendingIntent deliverStatus = PendingIntent.getBroadcast(context, 1, smsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return deliverStatus;
    }

    private static PendingIntent getSendResultPendingIntent(Context context, String phone) {
        Intent result = new Intent(SMS_SEND_STATUS).setClass(context, SmsReceiver.class);
        result.putExtra(SMS_PHONE, phone);
        PendingIntent showResult = PendingIntent.getBroadcast(context, 2, result, PendingIntent.FLAG_UPDATE_CURRENT);
        return showResult;
    }
}
