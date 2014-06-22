package de.hofmanns.traininganalyse;

import java.util.List;
import java.util.Random;

import de.hofmanns.traininganalyse.databse.TrainerDataSource;
import de.hofmanns.traininganalyse.databse.Training;
import android.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DisplayStoreDataActivity extends ListActivity {
	private TrainerDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_display_store_data);

		datasource = new TrainerDataSource(this);
		datasource.open();

		List<Training> values = datasource.getAllTrainings();

		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Training> adapter = new ArrayAdapter<Training>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);

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

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Training> adapter = (ArrayAdapter<Training>) getListAdapter();
		Training trainig = null;
		switch (view.getId()) {
		case R.id.add:
			String[] trainigs = new String[] { "Cool", "Very nice", "Hate it", "Hi" };
			int nextInt = new Random().nextInt(4);
			// save the new comment to the database
			trainig = datasource.createTraining(trainigs[nextInt], nextInt,
					nextInt, null);
			adapter.add(trainig);
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
