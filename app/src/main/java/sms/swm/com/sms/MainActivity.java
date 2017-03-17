package sms.swm.com.sms;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText phoneEdit;
    private EditText textEdit;
    private Button send;
    private Button sendInBackground;
    private Button sendInService;
    private Handler smsTimer;
    private SmsService smsService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SmsService.LocalBinder binder = (SmsService.LocalBinder) service;
            smsService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent service = new Intent(this, SmsService.class);
        bindService(service, connection, BIND_AUTO_CREATE);
        smsTimer = new Handler();

        setContentView(R.layout.activity_main);
        phoneEdit = (EditText) findViewById(R.id.sms_phone);
        textEdit = (EditText) findViewById(R.id.sms_text);
        send = (Button) findViewById(R.id.sms_send);
        send.setOnClickListener(this);
        sendInBackground = (Button) findViewById(R.id.sms_send_in_background);
        sendInBackground.setOnClickListener(this);
        sendInService = (Button)findViewById(R.id.sms_send_in_service);
        sendInService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sms_send:
                sendNow();
                break;
            case R.id.sms_send_in_background:
                sendLatter();
                break;
            case R.id.sms_send_in_service:
                startSmsService();
                break;
        }
    }

    private void startSmsService() {

        String receiver = String.valueOf(phoneEdit.getText());
        String sender = receiver;
        String text = String.valueOf(textEdit.getText());
        smsService.send(receiver, text);
    }

    private void sendNow() {
        String receiver = String.valueOf(phoneEdit.getText());
        String sender = receiver;
        String text = String.valueOf(textEdit.getText());

        SmsHelper.send(this, receiver, text);
    }

    private void sendLatter() {
        smsTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNow();
            }
        }, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
