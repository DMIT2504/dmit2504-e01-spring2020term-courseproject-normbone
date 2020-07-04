package ca.nait.dmit2504.callr;

import android.telecom.Call;
import android.telecom.VideoProfile;

import androidx.annotation.Nullable;

import io.reactivex.subjects.BehaviorSubject;

public class CurrentCom {

    public static BehaviorSubject<Integer> state = BehaviorSubject.create();
    private static Call call;

    // Creates an object which handles the current state of a call
    private Object callback = new Call.Callback(){
        @Override
        public void onStateChanged(Call call, int newState){
            super.onStateChanged(call, newState);
            state.onNext(newState);
        }

    };

    // Accepts a Call object with a certain value.  It will first check if the current call object
    // is still registered and unregister the current call object before registering the new call
    // object.  The new call object becomes the current call object.
    public final void setCall(@Nullable Call value){
        if (call != null) {
            call.unregisterCallback((Call.Callback)callback);
        }

        if (value != null) {
            value.registerCallback((Call.Callback)callback);
            state.onNext(value.getState());
        }

        call = value;
    }

    // Instructs the call object to open an audio connection with system
    public void connect() {
        assert call != null;
        call.answer(VideoProfile.STATE_AUDIO_ONLY);
    }

    // Instructs the call object to disconnect
    public void disconnect() {
        assert call != null;
        call.disconnect();
    }

    // Still unsure of how to ignore calls


    public static Call getCall() {
        return call;
    }
}
