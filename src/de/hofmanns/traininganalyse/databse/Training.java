package de.hofmanns.traininganalyse.databse;

import java.io.Serializable;
import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * @author Sabina Hofmann
 * 
 */
public class Training implements Parcelable {

	private long id;
	private String practiceType;
	private int rates;
	private int amount;
	private String created_at;
	int listPosition = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPracticeType() {
		return practiceType;
	}

	public void setPracticeType(String practiceType) {
		this.practiceType = practiceType;
	}

	public int getRates() {
		return rates;
	}

	public void setRates(int rates) {
		this.rates = rates;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	// used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return "Training :" + practiceType + "," + rates + "," + amount + ","
				+ created_at;
	}

	public int getListPosition() {
		return listPosition;
	}

	public void setListPosition(int listPosition) {
		this.listPosition = listPosition;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.practiceType);
		dest.writeInt(this.rates);
		dest.writeInt(this.amount);
		dest.writeString(this.created_at);
		dest.writeInt(this.listPosition);

	}

	public static final Parcelable.Creator<Training> CREATOR = new Parcelable.Creator<Training>() {
		public Training createFromParcel(Parcel in) {
			return new Training(in);
		}

		public Training[] newArray(int size) {
			return new Training[size];
		}
	};

	Training(Parcel in) {
		this.id = in.readLong();
		this.practiceType = in.readString();
		this.rates = in.readInt();
		this.amount = in.readInt();
		this.created_at = in.readString();
		this.listPosition = in.readInt();
	}

}
