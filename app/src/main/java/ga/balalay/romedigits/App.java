package ga.balalay.romedigits;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import androidx.annotation.NonNull;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		refWatcher = LeakCanary.install(this);

		Hawk.init(this)
				.setEncryption( new NoEncryption() )
				.build();

		bindService(new Intent(this, IncService.class), mConnection, Context.BIND_AUTO_CREATE);
	}

	/**
	 * Check object destroyed
	 */
	public static void checkLeak(@NonNull final Object object) {
		if (refWatcher != null) {
			refWatcher.watch(object);
		}
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		if (mBound) {
			mBound = false;
			unbindService(mConnection);
		}
	}

	@NonNull
	private final ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected( ComponentName className, IBinder service) {
			mBound = true;
		}

		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
		}
	};

	public boolean mBound;

	private static RefWatcher refWatcher;
}
