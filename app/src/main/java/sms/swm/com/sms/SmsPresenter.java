package sms.swm.com.sms;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static sms.swm.com.sms.DialogActivity.SMS_MESSAGE;
import static sms.swm.com.sms.SmsReceiver.SMS_DELIVER_REPORT;
import static sms.swm.com.sms.SmsReceiver.SMS_SEND_STATUS;

/**
 * Created by yangzhenyu on 2017/3/17.
 */

public class SmsPresenter implements View.OnClickListener{
    private Activity activity;
    private final TextView title;
    private final TextView sendStatus;
    private final TextView deliverReport;
    private final Button close;

    public SmsPresenter(Activity activity, TextView title, TextView sendStatus, TextView deliverReport, Button close) {
        this.activity = activity;
        this.title = title;
        this.sendStatus = sendStatus;
        this.deliverReport = deliverReport;
        this.close = close;
    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }

    public void offerIntent(Intent intent) {
        String action = intent.getAction();
        String phone = intent.getStringExtra(DialogActivity.SMS_PHONE);
        String title = activity.getString(R.string.sms_title, phone);
        this.title.setText(title);

        String message = intent.getStringExtra(DialogActivity.SMS_MESSAGE);

        if(TextUtils.equals(action, SMS_SEND_STATUS)) {
            sendStatus.setText(message);
        }

        if(TextUtils.equals(action, SMS_DELIVER_REPORT)) {
            deliverReport.setText(message);
            deliverReport.setVisibility(View.VISIBLE);
        }
    }

}
