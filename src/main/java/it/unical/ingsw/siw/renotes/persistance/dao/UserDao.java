package it.unical.ingsw.siw.renotes.persistance.dao;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;


public interface UserDao {
	
	public void save(User user); //Insert -Create
	public User findByPrimaryKey(String username, String mail);
	public void update(User user);
	public void delete(User user);
	
	public List<Ad> findBoughtAd(User user);
	public List<Ad> findManagedAd(User user);
	public List<PaymentMethod> findPaymentMethods(User user);
	
	public void insertBoughtAd(User user, Integer id);
	public void deleteBoughtAd(User user, Integer id);
	
	public void insertManagedAd(User user, Integer id);
	public void deleteManagedAd(User user, Integer id);
	
	public void insertPaymentMethods(User user, String cardNumber);
	public void deletePaymentMethods(User user, String cardNumber);
	
	public User findByUsername(String name);
	
	public String getVerificationCode(User user);
	public boolean getTwoFactorAutenticationActivated(User user);
	public void setVerificationCode(User user, String code);
	public void modifyUserPwd(User user, String password);
	public void modifyImage(User user, String image);
	public String getUserImage(User user);
	
	public User findByMail(String mail);
	
	public HashMap<Integer, Integer> findPopularAds(User user);
	
	public List<Bundle> findMyBundle(User user);
	
}
