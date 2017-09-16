package com.amazon.test;

public class GoodDetail {
	private String Date = new String();
	private String Format = new String();
	private String Review = new String();
	private String Label = new String();

	private GoodDetail() {
		// TODO Auto-generated constructor stub
	}

	public GoodDetail(String date, String format, String review, String label) {
		this.Date = date;
		this.Format = format;
		this.Review = review;
		this.Label = label;
	}

	public String getDate() {
		return Date;
	}

	public String getFormat() {
		return Format;
	}

	public String getReview() {
		return Review;
	}

	public String getLabel() {
		return Label;
	}
}
