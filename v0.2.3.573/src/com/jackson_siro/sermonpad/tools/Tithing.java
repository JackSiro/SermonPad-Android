package com.jackson_siro.sermonpad.tools;

public class Tithing {

	private int tithingid, tithing_state, tithing_amount;
	private String tithing_posted, tithing_place, tithing_source;
	
	public Tithing(){}
	
	public Tithing( String tithing_posted, int tithing_amount, 
			String tithing_source, String tithing_place, int tithing_state) {
		
		super();
		this.tithing_posted = tithing_posted;
		this.tithing_place = tithing_place;
		this.tithing_source = tithing_source;
		this.tithing_amount = tithing_amount;
		this.tithing_state = tithing_state;
	}
	
	public int getTithingid() {
		return tithingid;
	}
	public void setTithingid(int tithingid) {
		this.tithingid = tithingid;
	}
	public String getPosted() {
		return tithing_posted;
	}
	public void setPosted(String tithing_posted) {
		this.tithing_posted = tithing_posted;
	}
	
	public String getPlace() {
		return tithing_place;
	}
	public void setPlace(String tithing_place) {
		this.tithing_place = tithing_place;
	}

	public String getSource() {
		return tithing_source;
	}
	public void setSource(String tithing_source) {
		this.tithing_source = tithing_source;
	}
	
	public int getAmount() {
		return tithing_amount;
	}
	public void setAmount(int tithing_amount) {
		this.tithing_amount = tithing_amount;
	}

	public int getState() {
		return tithing_state;
	}
	public void setState(int tithing_state) {
		this.tithing_state = tithing_state;
	}
	
	@Override
	public String toString() {
		return "Note [tithingid=" + tithingid + ", tithing_posted=" + tithing_posted + 
				 ",  tithing_place=" + tithing_place + ",  tithing_source=" + tithing_source +  
				 ", tithing_amount=" + tithing_amount + ", tithing_state=" + tithing_state + "]";
	}
	
	
	
}
