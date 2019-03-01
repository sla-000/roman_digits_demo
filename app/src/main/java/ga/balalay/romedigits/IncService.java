package ga.balalay.romedigits;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.os.SystemClock.sleep;

public class IncService extends Service {
	private final static String TAG = "IncService";

	public IncService() {
	}

	public enum Cmd {
		START,
		STOP,
		UPDATE,
	}

	public static void send( @NonNull final Cmd cmd ) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "send: cmd=" + cmd );

		EventBus.getDefault().post(cmd);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onCreate:" );

		EventBus.getDefault().register(this);

		mThread = new Thread( () -> {
			if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "IncServiceThread: Start" );

			while( ! Thread.currentThread().isInterrupted() ) {
				sleep(1_000);

				if( ! mEnabled.get() ) {
					continue;
				}

				final int newVal = mVal.addAndGet( 1 );

				if( newVal > 100 ) {
					mVal.set( 1 );
				}

				Logic.send( mVal.get() );
			}

			if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "IncServiceThread: Exit" );
		}, "IncServiceThread" );

		mThread.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onDestroy:" );

		EventBus.getDefault().unregister(this);

		if( mThread != null ) {
			mThread.interrupt();
		}

		App.checkLeak( this );
	}

	@Override
	public IBinder onBind( Intent intent ) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onBind:" );

		return new Binder();
	}

	@Subscribe
	public void onEvent(@NonNull final Cmd cmd) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onEvent: cmd=" + cmd );

		switch(cmd) {
			case START: {
				mEnabled.set( true );
				break;
			}
			case STOP: {
				mEnabled.set( false );
				break;
			}
			case UPDATE: {
				Logic.send( mVal.get() );
				break;
			}
			default: {
				throw new RuntimeException( "cmd=" + cmd );
			}
		}
	}

	@Nullable
	private Thread mThread;

	@NonNull
	private final AtomicBoolean mEnabled = new AtomicBoolean();

	@NonNull
	private final AtomicInteger mVal = new AtomicInteger(1);
}
