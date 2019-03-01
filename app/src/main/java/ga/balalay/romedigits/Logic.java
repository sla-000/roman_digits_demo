package ga.balalay.romedigits;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;

public class Logic implements LogicControl {
	private final static String TAG = "Logic";

	public Logic( @NonNull ViewUpdate mViewUpdate ) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "Logic:" );

		EventBus.getDefault().register(this);

		this.mViewUpdate = mViewUpdate;

		IncService.send( IncService.Cmd.UPDATE );
	}

	public static void send( @NonNull final Integer val ) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "send: val=" + val );

		EventBus.getDefault().post(val);
	}

	@Override
	public void start() {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "start:" );

		IncService.send( IncService.Cmd.START );
	}

	@Override
	public void stop() {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "stop:" );

		IncService.send( IncService.Cmd.STOP );
	}

	public void dispose() {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "dispose:" );

		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent( @NonNull final Integer val) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onEvent: val=" + val );

		mViewUpdate.setValue( val );
	}

	@NonNull
	private final ViewUpdate mViewUpdate;
}
