package it.unical.ingsw.siw.renotes.model;

import java.util.List;

public class User {
	
	private String username;
	private String mail;
	private String password;
	private int rate;
	private String image;
	private Cart cart;
	private List<Ad> boughtAd;
	private List<Ad> managedAd;
	private List<PaymentMethod> paymentMethods;
	
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}
	
	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
	public List<Ad> getBoughtAd() {
		return boughtAd;
	}
	public void setBoughtAd(List<Ad> boughtAd) {
		this.boughtAd = boughtAd;
	}
	
	public List<Ad> getManagedAd() {
		return managedAd;
	}
	public void setManagedAd(List<Ad> managedAd) {
		this.managedAd = managedAd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
