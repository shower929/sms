package sms.swm.com.sms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.text.TextUtils;


public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_SEND_STATUS = "com.swm.sms.send";
    public static final String SMS_DELIVER_REPORT = "com.swm.sms.deliver";
    public static final String SMS_PHONE = "phone";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dialog = new Intent(context, DialogActivity.class);
        dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String action = intent.getAction();
        String phone = intent.getStringExtra(SMS_PHONE);
        dialog.putExtra(SMS_PHONE, phone);

        if (TextUtils.equals(action, SMS_SEND_STATUS)) {
            dialog.setAction(SMS_SEND_STATUS);

            int result = getResultCode();
            switch(result) {
                case Activity.RESULT_OK:
                    dialog.putExtra(DialogActivity.SMS_MESSAGE, context.getString(R.string.sms_sent));
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    dialog.putExtra(DialogActivity.SMS_MESSAGE, context.getString(R.string.sms_send_fail));
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    dialog.putExtra(DialogActivity.SMS_MESSAGE, context.getString(R.string.sms_radio_off));
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    dialog.putExtra(DialogActivity.SMS_MESSAGE, context.getString(R.string.sms_no_service));
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    dialog.putExtra(DialogActivity.SMS_MESSAGE, context.getString(R.string.sms_null_pdu));
                    break;
            }
        }

        if (TextUtils.equals(action, SMS_DELIVER_REPORT)) {
            dialog.setAction(SMS_DELIVER_REPORT);
            dialog.putExtra(DialogActivity.SMS_MESSAGE, context.getString(R.string.sms_delivered, phone));
        }

        context.startActivity(dialog);
    }
}
