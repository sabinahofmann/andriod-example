package de.hofmanns.traininganalyse;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.hofmanns.traininganalyse.databse.Training;

public class TrainingAdapter extends ArrayAdapter<Training> {

	private ArrayList<Training> trainingList;
	private Context context;

	public TrainingAdapter(Context context, ArrayList<Training> trainingList) {
		super(context, R.layout.row_data, trainingList);
		this.trainingList = new ArrayList<Training>();
		this.trainingList = trainingList;

		Log.d("ON CREATE", "adapter " + this.trainingList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Log.d("ON CREATE", "adapter getView");
		
		View rowView = convertView;
		if (convertView == null) {
			 LayoutInflater inflater =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_data, parent, false);
		}
		TextView practice_type = (TextView) rowView
				.findViewById(R.id.practice_type);
		TextView rates = (TextView) rowView.findViewById(R.id.rates);

		Training training = trainingList.get(position);
		training.setListPosition(position);

		practice_type.setText(training.getPracticeType());
		rates.setText(Integer.toString(training.getRates()));

		Log.d("ON CREATE", "training practice_type and rates ");

		rowView.setTag(training);

		Log.d("ON CREATE", "convertView " + rowView);

		return rowView;
	}

	public void setTraining(Training training, int position) {
		this.trainingList.set(position, training);
	}

}
