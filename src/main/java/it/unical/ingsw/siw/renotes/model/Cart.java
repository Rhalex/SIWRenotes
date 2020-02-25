package it.unical.ingsw.siw.renotes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cart {
	
	private int id;
	private double total;
	private Date date;
	private List<Ad> ads;
	private List<Bundle> bundles;
	
	
	public List<Bundle> getBundles() {
		return bundles;
	}

	public void setBundles(List<Bundle> bundles) {
		this.bundles = bundles;
	}

	public List<Ad> getAds() {
		
		return ads;
	}
	
	public void setAds(List<Ad> ads) {
		
		this.ads = ads;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public double getTotal() {
		this.total = 0;
		
		if(ads != null)
			for(Ad ad: ads)
				this.total += ad.getPrice();
		
		if(bundles != null)
			for(Bundle bundle: bundles)
				this.total += bundle.getPrice();
		
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Date getDate() {
		
		if(date == null)
			date = new Date();
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void clearCart()
	{
		if(ads!= null)
			this.ads.clear();
		
		if(bundles != null)
			this.bundles.clear();
		
		this.total = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		Cart cart = (Cart) obj;
		return id==cart.id;
	}
}
