package ca.nait.dmit2504.callr;

import android.telecom.Call;
import android.telecom.InCallService;

public class TelecomService extends InCallService {

    ContactDatabase mContactDatabase = new ContactDatabase(this);

    @Override
    public void onCallAdded(Call call){
        super.onCallAdded(call);

        if (mContactDatabase.isInContacts(call.getDetails().getHandle().toString().trim())){
            new CurrentCom().setCall(call);
            TelecomActivity.start(this, call);
        }else {
            call.disconnect();
        }

    }

    @Override
    public void onCallRemoved(Call call){
        super.onCallRemoved(call);
        new CurrentCom().setCall(null);
    }

}
