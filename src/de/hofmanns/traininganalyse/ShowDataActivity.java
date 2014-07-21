package de.hofmanns.traininganalyse;

import de.hofmanns.traininganalyse.databse.TrainerDataSource;
import de.hofmanns.traininganalyse.databse.Training;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class ShowDataActivity extends Activity implements OnClickListener {

	private Training training;
	private TextView type;
	private EditText practiceType, rates, amount, created_at;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_data);

		Bundle bundle = this.getIntent().getExtras();
		training = bundle.getParcelable("training");
		Log.d("SHOW DATA", "training "+training);

		Button save = (Button) findViewById(R.id.save);
		Button cancel = (Button) findViewById(R.id.cancel);
		save.setOnClickListener(this);
		cancel.setOnClickListener(this);

		Log.d("SHOW DATA", "set buttons");
		type  = (TextView)findViewById(R.id.type);		
		practiceType = (EditText) findViewById(R.id.practiceType);
		rates = (EditText) findViewById(R.id.rates);
		amount = (EditText) findViewById(R.id.amount);
		created_at = (EditText) findViewById(R.id.created_at);

		Log.d("SHOW DATA", "find r.id.xy");
		
		type.setText(training.getPracticeType());
		practiceType.setText(training.getPracticeType());
		rates.setText(String.valueOf(training.getRates()));
		amount.setText(String.valueOf(training.getAmount()));
		created_at.setText(training.getCreated_at());

		Log.d("SHOW DATA", "setText");
	}

	public void onClick(View v) {

		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.save:

			training.setPracticeType(practiceType.getText().toString());
			training.setRates(Integer.parseInt(rates.getText().toString()));
			training.setAmount(Integer.parseInt(amount.getText().toString()));
			training.setCreated_at(created_at.getText().toString());

			Bundle b = new Bundle();
			b.putParcelable("training", training);
			intent.putExtras(b);
			setResult(RESULT_OK, intent);
			finish();
			break;

		case R.id.cancel:
			setResult(RESULT_CANCELED, intent);
			finish();
			break;

		}

	}

}
