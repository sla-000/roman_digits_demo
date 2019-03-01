package ga.balalay.romedigits;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

import com.google.android.material.button.MaterialButton;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ControlFragment extends Fragment {
	public ControlFragment() {
	}

	@OnClick(R.id.idStart)
	public void clickStart( MaterialButton button) {
		final LogicControl logicControl = ((MainActivity)getActivity()).getLogicControl();

		logicControl.start();

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(startButton, "translationX",
					20, -20, 10, -10, 5, -5, 0);
			animation.setDuration(500);
			animation.setInterpolator( new BounceInterpolator() );
			animation.start();
		}

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(startButton, "translationY",
					20, -20, 10, -10, 5, -5, 0);
			animation.setDuration(500);
			animation.setInterpolator( new BounceInterpolator() );
			animation.start();
		}
	}

	@OnClick(R.id.idStop)
	public void clickStop(MaterialButton button) {
		final LogicControl logicControl = ((MainActivity)getActivity()).getLogicControl();

		logicControl.stop();

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(stopButton, "translationX",
					20, -20, 10, -10, 5, -5, 0);
			animation.setDuration(500);
			animation.setInterpolator( new BounceInterpolator() );
			animation.start();
		}

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(stopButton, "translationY",
					20, -20, 10, -10, 5, -5, 0);
			animation.setDuration(500);
			animation.setInterpolator( new BounceInterpolator() );
			animation.start();
		}
	}

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		final View view = inflater.inflate( R.layout.fragment_control, container, false );

		unbinder = ButterKnife.bind(this, view);

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(startButton, "alpha", 0.0f, 1.0f);
			animation.setDuration(2_000);
			animation.setInterpolator( new AccelerateDecelerateInterpolator() );
			animation.start();
		}

		{
			ObjectAnimator animation = ObjectAnimator.ofFloat(stopButton, "alpha", 0.0f, 1.0f);
			animation.setDuration(2_000);
			animation.setInterpolator( new AccelerateDecelerateInterpolator() );
			animation.start();
		}

		return view;
	}

	@Override public void onDestroyView() {
		super.onDestroyView();

		unbinder.unbind();

		App.checkLeak( this );
	}

	@BindView(R.id.idStart)
	MaterialButton startButton;

	@BindView(R.id.idStop)
	MaterialButton stopButton;

	private Unbinder unbinder;
}
