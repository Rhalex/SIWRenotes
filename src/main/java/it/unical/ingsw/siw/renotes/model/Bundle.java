package it.unical.ingsw.siw.renotes.model;

import java.util.List;

public class Bundle {
	private int bundle_id;
	private String title;
	private double price;
	private String image;
	private User user;
	private List<Ad> ads;
	
	
	public int getBundle_id() {
		return bundle_id;
	}

	public void setBundle_id(int bundle_id) {
		this.bundle_id = bundle_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Ad> getAds() {
		return ads;
	}
	
	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Bundle bundle = (Bundle) obj;
		return bundle_id == bundle.getBundle_id();
	}
}
