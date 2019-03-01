package ga.balalay.romedigits;

import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.SENSOR_SERVICE;


public class DigitsFragment extends Fragment implements ViewUpdate {
	private final static String TAG = "DigitsFragment";

	public DigitsFragment() {
	}

	@Override
	public void setValue( final int val ) {
		mVal = val;

		textDigits.setText( RomanDigits.toRoman( mVal ) );

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(textDigits, "scaleY", 1.5f, 1.0f);
			animation.setDuration(700);
			animation.setInterpolator( new BounceInterpolator() );
			animation.start();
		}

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(textDigits, "scaleX", 3.0f, 1.0f);
			animation.setDuration(700);
			animation.setInterpolator( new BounceInterpolator() );
			animation.start();
		}

		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			ObjectAnimator animation = ObjectAnimator.ofArgb(textDigits, "textColor", getNextColor());
			animation.setDuration(700);
			animation.setInterpolator( new AccelerateDecelerateInterpolator() );
			animation.start();
		}
	}

	@OnClick( R.id.textDigits )
	void clickDigits() {
		ObjectAnimator animation = ObjectAnimator.ofFloat(textDigits, "rotation",
				40, -35, 30, -25, 20, -15, 10, -5, 0);
		animation.setDuration(700);
		animation.setInterpolator( new AccelerateDecelerateInterpolator() );
		animation.start();
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onCreateView:" );

		final View view = inflater.inflate( R.layout.fragment_digits, container, false );

		unbinder = ButterKnife.bind(this, view);

		mVal = Hawk.get( VAL_KEY, 1 );
		setValue( mVal );

		sensorManager=(SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener( mAccelListener,
				sensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		return view;
	}

	@Override public void onDestroyView() {
		super.onDestroyView();

		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onDestroyView:" );

		Hawk.put( VAL_KEY, mVal );

		sensorManager.unregisterListener( mAccelListener );

		unbinder.unbind();

		App.checkLeak( this );
	}

	@NonNull
	private final SensorEventListener mAccelListener = new SensorEventListener() {
		@Override
		public void onSensorChanged( SensorEvent event ) {
			{
				averagingX = event.values[0] - (averagingX * 1/AVERAGING);

				ObjectAnimator animation = ObjectAnimator.ofFloat(textDigits, "translationX",
						- averagingX / AVERAGING * TRANS_KOEFF, 0.0f);
				animation.setDuration(1000);
				animation.setInterpolator( new AccelerateDecelerateInterpolator() );
				animation.start();
			}

			{
				averagingY = event.values[1] - (averagingY * 1/AVERAGING);

				ObjectAnimator animation = ObjectAnimator.ofFloat(textDigits, "translationY",
						averagingY / AVERAGING * TRANS_KOEFF, 0.0f);
				animation.setDuration(1000);
				animation.setInterpolator( new AccelerateDecelerateInterpolator() );
				animation.start();
			}
		}

		@Override
		public void onAccuracyChanged( Sensor sensor, int accuracy ) {
		}
	};

	private int getNextColor() {
		final int r = (int) (Math.random() * 200);
		final int g = (int) (Math.random() * 200);
		int b = 400 - r - g;
		if( b > 255 ) {
			b = 255;
		}

		return (0xFF << 24) | (r << 16) | (g << 8) | b;
	}

	private final static String VAL_KEY = "VAL_KEY";

	private float averagingX;
	private float averagingY;

	private final static float AVERAGING = 8;
	private final static float TRANS_KOEFF = 50;

	@BindView( R.id.textDigits )
	TextView textDigits;

	private int mVal = 1;

	private SensorManager sensorManager;

	private Unbinder unbinder;
}
