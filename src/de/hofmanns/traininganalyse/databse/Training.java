package de.hofmanns.traininganalyse.databse;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * @author Sabina Hofmann
 *
 */
public class Training implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String practiceType;
	private int rates;
	private int amount;
	private Date created_at;
	
	
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
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	//used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return "Training :"+practiceType+","+rates+","+amount+","+created_at;
	}

}
