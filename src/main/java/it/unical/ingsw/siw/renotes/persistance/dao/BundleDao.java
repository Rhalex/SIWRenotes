package it.unical.ingsw.siw.renotes.persistance.dao;

import java.util.List;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.User;

public interface BundleDao {
	public void save(Bundle bundle); //Insert -Create
	public Bundle findByPrimaryKey(Integer id);

	public void delete(Bundle bundle);
	
	public void insertAd(Bundle bundle, Ad ad, String index);
	
	public List<Bundle> findAllNoSession();
	public List<Bundle> findAll(User user);
}
