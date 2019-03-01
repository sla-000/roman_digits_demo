package ga.balalay.romedigits;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Разработать для  Android  приложение, которое использует фоновый сервис.
 *
 * ·         Фоновый сервис каждую секунду оповещает приложение о прогрессе роста некой величины X.
 *
 * ·         Начальное значение  X = 1, конечное X  = 100. Шаг изменения: 1. После достижения 100 значение X устанавливается в 1.
 *
 * ·         В приложении должна быть предусмотрена кнопка, которая устанавливает счетчик на паузу и снимает счетчик с паузы.
 *
 * ·         Приложение должно визуализировать значение X римскими цифрами.
 *
 * ·         Приложение должно корректно реагировать на смену ориентации экрана.
 *
 * ·         Достаточно задействовать  API Level  26 (Android  8.0 и выше).
 *
 * ·         Дополнительно приветствуется, если будет задействована какая-либо анимация при визуализации значений X.
 */
public class MainActivity extends AppCompatActivity {
	private final static String TAG = "MainActivity";

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onCreate:" );

		setContentView( R.layout.activity_main );

		mDigitsFragment = new DigitsFragment();
		mControlFragment = new ControlFragment();

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame1, mDigitsFragment)
				.replace(R.id.frame2, mControlFragment)
				.commit();
	}

	@Override
	protected void onStart() {
		super.onStart();

		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onStart:" );

		mLogic = new Logic( mDigitsFragment );
	}

	@Override
	protected void onStop() {
		super.onStop();

		if(Log.isLoggable(TAG, Log.VERBOSE) ) Log.v( TAG, "onStop:" );

		mLogic.dispose();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		App.checkLeak( this );
	}

	public LogicControl getLogicControl() {
		return mLogic;
	}

	private DigitsFragment mDigitsFragment;
	private ControlFragment mControlFragment;

	private Logic mLogic;

}
