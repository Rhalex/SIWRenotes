package it.unical.ingsw.siw.renotes.persistance.dao;

import java.util.List;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;

public interface CartDao {
	public void save(Cart cart); //Insert -Create
	public Cart findByPrimaryKey(Integer id);
	public void update(Cart cart);
	public void delete(Cart cart);
	
	public void insertAd(Integer cartId, Integer adId);
	public void deleteAd(Integer cartId, Integer adId);
	
	public List<Ad> listOfAds(Cart cart); 
	public int lastSerialCartId();
	
	public void insertBundle(Integer cartId, Integer bundleId);
	public void deleteBundle(Integer cartId, Integer bundleId);
	
	public List<Bundle> listOfBundles(Cart cart);

}
