package it.unical.ingsw.siw.renotes.model;

import java.io.File;
import java.util.List;

public class Ad implements Comparable<Ad> {

	private int id;
	private String title;
	private String subject;
	private String university;
	private String degreeCourse;
	private String description;
	private double price;
	private String file;
	private boolean consultable;
	private Preview preview;
	
	private int value;
	private double version;
	
	
	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public boolean isConsultable() {
		return consultable;
	}
	
	public void setConsultable(boolean consultable) {
		this.consultable = consultable;
	}
	
	public Preview getPreview() {
		return preview;
	}
	
	public void setPreview(Preview preview) {
		this.preview = preview;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getDegreeCourse() {
		return degreeCourse;
	}
	public void setDegreeCourse(String degreeCourse) {
		this.degreeCourse = degreeCourse;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public int[] getStat(List<Review> reviews)
	{
		int statistics[] = {0,0,0};
		
		if(reviews.size()>0)
		{
			for(Review r: reviews)
			{
				statistics[0] += r.getQuality(); 
				statistics[1] += r.getReliability(); 
				statistics[2] += r.getCompleteness(); 
			}
			
			for(int i=0; i<3; i++)
				if(statistics[i] != 0)
					statistics[i] /= reviews.size();
		}
		
		return statistics;
	}
	
	@Override
	public boolean equals(Object obj) {

		Ad ad = (Ad) obj;
		return id==ad.id;
	}

	public void setValue(List<Review> reviews) {
		
		int statistics[] = getStat(reviews);
		
		this.value = 0;
		this.value += statistics[0] + statistics[1] + statistics[2];		
		this.value /= 3;
		
	}
	
	public int getValue()
	{
		return value;
	}

	public int compareTo(Ad o) {
		Integer v1 = new Integer(value);
		Integer v2 = new Integer(o.value);
		return v1.compareTo(v2);
	}

	@Override
	public String toString() {
		return "Ad [id=" + id + ", title=" + title + ", subject=" + subject + ", university=" + university
				+ ", degreeCourse=" + degreeCourse + ", description=" + description + ", price=" + price + ", file="
				+ file + ", consultable=" + consultable + ", value=" + value + "]";
	}
	
	
}
