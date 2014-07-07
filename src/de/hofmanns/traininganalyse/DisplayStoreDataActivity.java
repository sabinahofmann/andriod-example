package de.hofmanns.traininganalyse;

import java.util.List;

import de.hofmanns.traininganalyse.databse.TrainerDataSource;
import de.hofmanns.traininganalyse.databse.Training;
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

public class DisplayStoreDataActivity extends ListActivity {
	private TrainerDataSource datasource;
	final Context context = this;
	EditText input_practice_type = null;
	EditText input_rates = null;
	EditText input_amount = null;
	Training trainig = null;
	@SuppressWarnings("unchecked")
	ArrayAdapter<Training> adapter = (ArrayAdapter<Training>) getListAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_display_store_data);

		datasource = new TrainerDataSource(this);
		datasource.open();

		List<Training> values = datasource.getAllTrainings();

		// use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<Training> adapter = new ArrayAdapter<Training>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		ListView lv = getListView();
		lv.setClickable(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

		         // We know the View is a TextView so we can cast it
//		         TextView clickedView = (TextView) view;
//		         Toast.makeText(DisplayStoreDataActivity.this, "Item with id ["+id+"] - Position ["+position+"] - Planet ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
//				final String item = (String) parent.getItemAtPosition(position);

				Intent intent = new Intent(DisplayStoreDataActivity.this, ShowDataActivity.class);
//				String item = (String) parent.getItemAtPosition(position);
			
//			    String message = (String) view.getContext().getText(position);
			    intent.putExtra("training", datasource.getTraining((int) id));
			    startActivity(intent);

			}
		});

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
//		final ArrayAdapter<Training> adapter = (ArrayAdapter<Training>) getListAdapter();
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
