package ca.nait.dmit2504.telecomapp;

import android.telecom.Call;
import android.telecom.InCallService;

public class TelecomService extends InCallService {


    @Override
    public void onCallAdded(Call call){
        super.onCallAdded(call);
        new CurrentCom().setCall(call);
        TelecomActivity.start(this, call);
    }

    @Override
    public void onCallRemoved(Call call){
        super.onCallRemoved(call);
        new CurrentCom().setCall(null);
    }


}
