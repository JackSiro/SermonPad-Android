package com.jackson_siro.sermonpad.tools;

public class Sermon {

	private int sermonid, sermon_state;
	private String sermon_date, sermon_title, sermon_place, sermon_preacher, sermon_content, sermon_extra;
	
	public Sermon(){}
	
	public Sermon( String sermon_date, String sermon_title, String sermon_place, 
			String sermon_preacher, String sermon_content, String sermon_extra, int sermon_state) {
		super();
		this.sermon_date = sermon_date;
		this.sermon_title = sermon_title;
		this.sermon_place = sermon_place;
		this.sermon_preacher = sermon_preacher;
		this.sermon_content = sermon_content;
		this.sermon_extra = sermon_extra;
		this.sermon_state = sermon_state;
	}
	
	public int getSermonid() {
		return sermonid;
	}
	public void setSermonid(int sermonid) {
		this.sermonid = sermonid;
	}
	public String getDate() {
		return sermon_date;
	}
	public void setDate(String sermon_date) {
		this.sermon_date = sermon_date;
	}
	
	public String getTitle() {
		return sermon_title;
	}
	public void setTitle(String sermon_title) {
		this.sermon_title = sermon_title;
	}

	public String getPlace() {
		return sermon_place;
	}
	public void setPlace(String sermon_place) {
		this.sermon_place = sermon_place;
	}

	public String getPreacher() {
		return sermon_preacher;
	}
	public void setPreacher(String sermon_preacher) {
		this.sermon_preacher = sermon_preacher;
	}
	
	public String getContent() {
		return sermon_content;
	}
	public void setContent(String sermon_content) {
		this.sermon_content = sermon_content;
	}

	public String getExtra() {
		return sermon_title;
	}
	public void setExtra(String sermon_extra) {
		this.sermon_extra = sermon_extra;
	}

	public int getState() {
		return sermon_state;
	}
	public void setState(int sermon_state) {
		this.sermon_state = sermon_state;
	}
	
	@Override
	public String toString() {
		return "Note [sermonid=" + sermonid + ", sermon_date=" + sermon_date + ",  sermon_title=" + sermon_title
				 + ",  sermon_place=" + sermon_place + ",  sermon_preacher=" + sermon_preacher +  
				 ", sermon_content=" + sermon_content + ", sermon_extra=" + sermon_extra + ", sermon_state=" + sermon_state + "]";
	}
	
	
	
}
