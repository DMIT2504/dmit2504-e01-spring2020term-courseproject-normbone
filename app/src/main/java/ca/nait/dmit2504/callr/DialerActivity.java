package ca.nait.dmit2504.telecomapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;


public class DialerActivity extends AppCompatActivity {

    private String mPhoneNumber;
    StringBuilder sb = new StringBuilder();
    TextView mDisplayedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        mDisplayedNumber = findViewById(R.id.tv_activity_dialer_phone_number);

        if (getIntent() != null && getIntent().getData() != null) {
            mDisplayedNumber.setText(getIntent().getData().getSchemeSpecificPart());
        }

        updateDisplayedPhoneNumber();

    }

    @Override
    protected void onStart() {
        super.onStart();
        offerReplacingDefaultDialer();


    }

    public void updateDisplayedPhoneNumber() {

        mPhoneNumber = sb.toString();
        mDisplayedNumber.setText(mPhoneNumber);
    }

    public void onClickDeleteButton(View view) {
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        updateDisplayedPhoneNumber();
    }

    public void onClickTextButton(View view) {
    }

    public void onClick0Button(View view) {
        sb.append('0');
        updateDisplayedPhoneNumber();
    }

    public void onClickStarButton(View view) {
        sb.append('*');
        updateDisplayedPhoneNumber();
    }

    public void onClickHashButton(View view) {
        sb.append('#');
        updateDisplayedPhoneNumber();
    }

    public void onClick8Button(View view) {
        sb.append('8');
        updateDisplayedPhoneNumber();
    }

    public void onClick7Button(View view) {
        sb.append('7');
        updateDisplayedPhoneNumber();
    }

    public void onClick9Button(View view) {
        sb.append('9');
        updateDisplayedPhoneNumber();
    }

    public void onClick5Button(View view) {
        sb.append('5');
        updateDisplayedPhoneNumber();
    }

    public void onClick4Button(View view) {
        sb.append('4');
        updateDisplayedPhoneNumber();
    }

    public void onClick6Button(View view) {
        sb.append('6');
        updateDisplayedPhoneNumber();
    }

    public void onClick2Button(View view) {
        sb.append('2');
        updateDisplayedPhoneNumber();
    }

    public void onClick1Button(View view) {
        sb.append('1');
        updateDisplayedPhoneNumber();
    }

    public void onClick3Button(View view) {
        sb.append('3');
        updateDisplayedPhoneNumber();
    }

    public void onClickCallButton(View view) {
        Uri uri = Uri.parse("tel:" + mPhoneNumber.trim());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, uri));
    }

    private void offerReplacingDefaultDialer() {
        TelecomManager telecomManager = (TelecomManager) getSystemService(TELECOM_SERVICE);

        assert telecomManager != null;
        if (!getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
            Intent intent = new Intent(ACTION_CHANGE_DEFAULT_DIALER)
                    .putExtra(EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, getPackageName());
            startActivity(intent);
        }
    }

}