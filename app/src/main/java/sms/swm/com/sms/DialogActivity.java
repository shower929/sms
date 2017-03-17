package sms.swm.com.sms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends AppCompatActivity {
    public static final String SMS_PHONE = "phone";
    public static final String SMS_MESSAGE = "message";
    private SmsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);
        TextView title = (TextView) findViewById(R.id.sms_title);
        TextView sendStatus = (TextView) findViewById(R.id.sms_send_status);
        TextView deliverReport = (TextView) findViewById(R.id.sms_deliver_report);
        Button close = (Button) findViewById(R.id.sms_close);
        presenter = new SmsPresenter(this, title, sendStatus, deliverReport, close);
        close.setOnClickListener(presenter);
        Intent intent = getIntent();

        presenter.offerIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.offerIntent(intent);
    }
}
