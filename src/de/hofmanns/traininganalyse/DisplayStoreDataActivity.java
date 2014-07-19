package de.hofmanns.traininganalyse;

import java.util.ArrayList;

import de.hofmanns.traininganalyse.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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



public class DisplayStoreDataActivity extends ListActivity {
	private TrainerDataSource datasource;
	final Context context = this;
	EditText input_practice_type = null;
	EditText input_rates = null;
	EditText input_amount = null;
	Training trainig = null;
	@SuppressWarnings("unchecked")
	ArrayAdapter<Training> adapter = (ArrayAdapter<Training>) getListAdapter();

	private TrainingAdapter dataAdapter = null;
	private View currentView = null;

	private static final int TRAINING_EDIT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_store_data);
		// Generate list View from ArrayList
		displayListView();

	}

	private void displayListView() {

		datasource = new TrainerDataSource(this);
		datasource.open();

		ArrayList<Training> values = datasource.getAllTrainings();

		// use the SimpleCursorAdapter to show the elements in a ListView
		// ArrayAdapter<Training> adapter = new ArrayAdapter<Training>(this,
		// android.R.layout.simple_list_item_1, values);
		// setListAdapter(adapter);

		// create an ArrayAdaptar from the String Array
		dataAdapter = new TrainingAdapter(this, R.layout.row_data, values);
		ListView listView = (ListView) findViewById(R.id.list);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				currentView = view;

				// get reference to the training Object
				Training training = (Training) view.getTag();
				Toast.makeText(getApplicationContext(),
						training.getPracticeType(), Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(DisplayStoreDataActivity.this,
						ShowDataActivity.class);
				Bundle b = new Bundle();
				// pass the country object as a parcel
				b.putParcelable("training", training);
				intent.putExtras(b);
				startActivityForResult(intent, TRAINING_EDIT);

			}
		});

	}

	// @Override
	// public void onListItemClick(ListView l, View view, int position, long id)
	// {
	// // TODO Auto-generated method stub
	// super.onListItemClick(l, view, position, id);
	// Log.d("TRAINING", "Selected id =" + id);
	// trainig = (Training) l.getItemAtPosition(position);
	// Log.d("TRAINING", "Selected Training = {" + trainig.toString() + " }");
	// Intent intent = new Intent(DisplayStoreDataActivity.this,
	// ShowDataActivity.class);
	// // intent.putExtra("t_id", trainig.getId());
	// // intent.putExtra("t_practiceType", trainig.getPracticeType());
	// // intent.putExtra("t_rates", trainig.getRates());
	// // intent.putExtra("t_amountL", trainig.getAmount());
	// // intent.putExtra("t_date", trainig.getCreated_at());
	// intent.putExtra("training", trainig.toString());
	// startActivity(intent);
	// }

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
				dataAdapter.setTraining(training, listPosition);

				// update the country name in the ListView
				currentView.setTag(training);
				TextView name = (TextView) currentView.findViewById(R.id.name);
				name.setText(training.getPracticeType());
			}
			break;
		}
	}

	private class TrainingAdapter extends ArrayAdapter<Training> {

		private ArrayList<Training> trainingList;

		public TrainingAdapter(Context context, int textViewResourceId,
				ArrayList<Training> trainingList) {

			super(context, textViewResourceId, trainingList);
			this.trainingList = new ArrayList<Training>();
			this.trainingList.addAll(trainingList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			TextView code = null;
			TextView name = null;

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.row_data, null);

				code = (TextView) convertView.findViewById(R.id.code);
				name = (TextView) convertView.findViewById(R.id.name);
			}

			Training training = trainingList.get(position);
			training.setListPosition(position);
			code.setText(training.getPracticeType());
			name.setText(training.getRates());
			convertView.setTag(training);

			return convertView;
		}

		public void setTraining(Training training, int position) {
			this.trainingList.set(position, training);
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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_display_store_data, container, false);
			return rootView;
		}
	}

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
									trainig = datasource.createTraining(
											input_practice_type.getText()
													.toString(), Integer
													.parseInt(input_rates
															.getText()
															.toString()),
											Integer.parseInt(input_amount
													.getText().toString()));
									adapter.add(trainig);
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
			if (getListAdapter().getCount() > 0) {
				trainig = (Training) getListAdapter().getItem(0);
				datasource.deleteTraining(trainig);
				adapter.remove(trainig);
			}
			break;
		}
		adapter.notifyDataSetChanged();
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
