package de.hofmanns.traininganalyse;

//import android.R;
import java.util.ArrayList;
import android.annotation.SuppressLint;
//import de.hofmanns.traininganalyse.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.hofmanns.traininganalyse.databse.TrainerDataSource;
import de.hofmanns.traininganalyse.databse.Training;

public class DisplayStoreDataActivity extends Activity {
	private TrainerDataSource datasource;
	final Context context = this;
	EditText input_practice_type = null;
	EditText input_rates = null;
	EditText input_amount = null;
	Training training = null;

	// private TrainingAdapter dataAdapter = null;
	private ArrayAdapter<Training> dataAdapter = null;
	private View currentView = null;

	private static final int TRAINING_EDIT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_store_data);

		datasource = new TrainerDataSource(this);
		Log.d("ON CREATE", "datasource");
		datasource.open();

//		ArrayList<Training> trainingList = new ArrayList<Training>();
//		Training training = new Training("Hintern", 10, 3, null);
//		trainingList.add(training);
//		training = new Training("Arme", 13, 4, null);
//		trainingList.add(training);
		
		ArrayList<Training> trainingList = datasource.getAllTrainings();
		Log.d("ON CREATE", "trainingList");

		// create an ArrayAdaptar from the String Array
		 dataAdapter = new TrainingAdapter(this,
		 trainingList);

		ListView listView = (ListView) findViewById(R.id.listView1);
		Log.d("ON CREATE", "listView " + listView);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		Log.d("ON CREATE", "setAdapter");

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				currentView = view;
				 

				// get reference to the training Object
				Training training = (Training) view.getTag();
				Log.d("TRAINING", "Selected Training = {" + training.toString() + " }");
				Toast.makeText(getApplicationContext(),
						training.getPracticeType(), Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(DisplayStoreDataActivity.this,
						ShowDataActivity.class);
				Log.d("TRAINING", "intent");
				Bundle b = new Bundle();
				// pass the training object as a parcel
				b.putParcelable("training", training);
				Log.d("TRAINING", " pass the training object as a parcel");
				intent.putExtras(b);
				startActivityForResult(intent, TRAINING_EDIT);
				Log.d("TRAINING", "startActivityForResult");

			}
		});

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case TRAINING_EDIT:
			if (resultCode == RESULT_OK) {

				// read the bundle and get the country object
				Bundle bundle = data.getExtras();
				Training training = bundle.getParcelable("training");

				// update the country object in the ArrayAdapter
				int listPosition = training.getListPosition();
				// dataAdapter.setTraining(training, listPosition);

				// update the country name in the ListView
				currentView.setTag(training);
				TextView parctice_type = (TextView) currentView
						.findViewById(R.id.practice_type);
				parctice_type.setText(training.getPracticeType());
			}
			break;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_store_data, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(
	// R.layout.activity_display_store_data, container, false);
	// return rootView;
	// }
	// }

	// Will be called via the onClick attribute of the buttons in main.xml
	public void onClick(View view) {
		// final ArrayAdapter<Training> adapter = (ArrayAdapter<Training>)
		// getListAdapter();

		switch (view.getId()) {
		case R.id.add:
			// get prompts.xml view
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View promptView = layoutInflater.inflate(R.layout.promts, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			input_practice_type = (EditText) promptView
					.findViewById(R.id.input_practice_type);
			input_rates = (EditText) promptView.findViewById(R.id.input_rates);
			input_amount = (EditText) promptView
					.findViewById(R.id.input_amount);

			// setup a dialog window
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// get user input and set it to result
									training = datasource.createTraining(
											input_practice_type.getText()
													.toString(), Integer
													.parseInt(input_rates
															.getText()
															.toString()),
											Integer.parseInt(input_amount
													.getText().toString()));
									dataAdapter.add(training);
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});

			// create an alert dialog
			AlertDialog alertD = alertDialogBuilder.create();
			alertD.show();
			break;
		case R.id.delete:
			// if (getListAdapter().getCount() > 0) {
			// training = (Training) getListAdapter().getItem(0);
			// training = get
			// datasource.deleteTraining(training);
			// dataAdapter.remove(training);
			// }
			break;
		}
		dataAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
