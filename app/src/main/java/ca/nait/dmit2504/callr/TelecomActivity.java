package ca.nait.dmit2504.callr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

public class TelecomActivity extends AppCompatActivity {

    private String mPhoneNumber;
    private CurrentCom mCurrentCom;
    TextView mNumberTV;
    Button mIgnore;
    Button mConnect;
    Button mDisconnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telecom);

        mCurrentCom = new CurrentCom();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                |WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                |WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mIgnore = findViewById(R.id.bn_activity_telecom_ignore);
        mConnect = findViewById(R.id.bn_activity_telecom_connect);
        mDisconnect = findViewById(R.id.bn_activity_telecom_disconnect);
        mNumberTV = findViewById(R.id.tv_activity_telecom_number);

        // Collects the phone number data from the intent that initiated the TelecomActivity
        mPhoneNumber = Objects.requireNonNull(Objects.requireNonNull(getIntent().getData()).getSchemeSpecificPart());

    }

    public void onIgnore(View view) {
        mCurrentCom.ignore();
        this.finishAndRemoveTask();
    }

    public void onDisconnect(View view) {
        mCurrentCom.disconnect();
        this.finishAndRemoveTask();
    }

    public void onConnect(View view) {
        mCurrentCom.connect();
        checkStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStatus();
    }

    // Called by the TelecomService to start the TelecomActivity, flags it as activate to move it
    // to the top of the stack.  Accepts a Call object containing details about the call as well
    // as its affiliated phone number.
    public static void start(Context context, Call call){
        Intent intent = new Intent(context, TelecomActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setData(call.getDetails().getHandle());
        context.startActivity(intent);
    }

    public void incommingCall(){
        mConnect.setVisibility(View.VISIBLE);
        mIgnore.setVisibility(View.VISIBLE);
        mDisconnect.setVisibility(View.GONE);
    }

    public void activeCall(){
        mConnect.setVisibility(View.GONE);
        mIgnore.setVisibility(View.GONE);
        mDisconnect.setVisibility(View.VISIBLE);
    }

    public void checkStatus(){
        // Sets the internal phone number to display in the UI TextView
        mNumberTV.setText(mPhoneNumber);

        if (CurrentCom.state.getValue() == Call.STATE_DIALING
                || CurrentCom.state.getValue() == Call.STATE_ACTIVE
                || CurrentCom.state.getValue() == Call.STATE_CONNECTING){
            activeCall();
        }else if (CurrentCom.state.getValue() == Call.STATE_RINGING){
            incommingCall();
        }
    }
}