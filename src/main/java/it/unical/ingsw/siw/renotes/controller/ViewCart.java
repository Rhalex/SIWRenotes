package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewCart extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Cart cart = null;
		User user = null;
		
		List<Ad> adsTemp = new ArrayList<Ad>();
		List<Bundle> bundlesTemp = new ArrayList<Bundle>();
		
		if(req.getSession().getAttribute("userSession") == null)
		{
			cart = (Cart) req.getSession().getAttribute("cartSession");
			adsTemp = cart.getAds();
			bundlesTemp = cart.getBundles();
		
		}
		
		else if(req.getSession().getAttribute("userSession") != null && req.getSession().getAttribute("cartSession") == null)
		{
			user = (User) req.getSession().getAttribute("userSession");
			cart = user.getCart();
			adsTemp = user.getCart().getAds();
			bundlesTemp = user.getCart().getBundles();
		}
		else if(req.getSession().getAttribute("userSession") != null && req.getSession().getAttribute("cartSession") != null)
		{
			//MERGE DEI DUE CARRELLI
			user = (User) req.getSession().getAttribute("userSession");
			cart = (Cart) req.getSession().getAttribute("cartSession");
			
			//ELIMINAZIONE DI INSERZIONI PRESENTI NEI DUE CARRELLI E DI QUELLE NON VISUALIZZABILI DALL'UTENTE
			Set<Ad> adUser = new HashSet<Ad>(user.getCart().getAds());
			Set<Ad> adCart = new HashSet<Ad>(cart.getAds());
			
			adUser.addAll(adCart);
			
			List<Ad> adsUser = new ArrayList<Ad>(adUser);
			
			List<Ad> adManaged = DBManager.getInstance().getUserDao().findManagedAd(user);
			List<Ad> adBought = DBManager.getInstance().getUserDao().findBoughtAd(user);
			
			for(Ad ad: adManaged)
				adsUser.remove(ad);
			
			for(Ad ad: adBought)
				adsUser.remove(ad);
			
			//user.getCart().setAds(adsUser);
			
			//ELIMINAZIONE DI BUNDLES PRESENTI NEI DUE CARRELLI E DI QUELLI NON VISUALIZZABILI DALL'UTENTE
			Set<Bundle> bundleUser = new HashSet<Bundle>(user.getCart().getBundles());
			Set<Bundle> bundleCart = new HashSet<Bundle>(cart.getBundles());
			
			bundleUser.addAll(bundleCart);
			
			List<Bundle> bundlesUser = new ArrayList<Bundle>(bundleUser);
			
			List<Bundle> myBundle = DBManager.getInstance().getUserDao().findMyBundle(user);
			
			for(Bundle bd : myBundle)
				bundlesUser.remove(bd);
			
			user.getCart().setBundles(bundlesUser);
			
//			//ELIMINAZIONE DI INSERZIONI GIÀ PRESENTI IN UN BUNDLE
//			Set<Integer> adsIdSet = new HashSet<Integer>();
//			
//			for(Bundle bd : bundlesUser)
//				for(Ad ad : bd.getAds())
//					adsIdSet.add(ad.getId());
//			
//			List<Integer> adsId = new ArrayList<Integer>(adsIdSet);
//			
//			for(Integer id : adsId)
//			{
//				Ad temp = new Ad();
//				temp.setId(id);
//				
//				adsUser.remove(temp);
//			}
			
			user.getCart().setAds(adsUser);
			
			req.getSession().removeAttribute("cartSession");
			
		}
		
		List<Ad> ads = new ArrayList<Ad>();
		
		if(adsTemp.size()>0)
			for(Ad ad: adsTemp)
			{
				Ad adTemp = DBManager.getInstance().getAdDao().findByPrimaryKey(ad.getId());
				ads.add(adTemp);
			}
				
		List<Bundle> bundles = new ArrayList<Bundle>();
		
		if(bundlesTemp.size()>0)
		{
			for(Bundle bd : bundlesTemp)
			{
				Bundle bdTemp = DBManager.getInstance().getBundleDao().findByPrimaryKey(bd.getBundle_id());
				bundles.add(bdTemp);
			}
			
			//ELIMINAZIONE DI INSERZIONI GIÀ PRESENTI IN UN BUNDLE
			Set<Integer> adsIdSet = new HashSet<Integer>();
			
			for(Bundle bd : bundles)
				for(Ad ad : bd.getAds())
					adsIdSet.add(ad.getId());
			
			List<Integer> adsId = new ArrayList<Integer>(adsIdSet);
			
			for(Integer id : adsId)
			{
				Ad temp = new Ad();
				temp.setId(id);
				
				ads.remove(temp);
			}
			
			cart.setAds(ads);
			cart.setTotal(cart.getTotal());
		}
		
		if(ads.size()>0)
			req.setAttribute("ads", ads);
			
		if(bundles.size()>0)
			req.setAttribute("bundles", bundles);
		
		if( user != null)
			req.setAttribute("cart", user.getCart());
		else
			req.setAttribute("cart", cart);
	
		RequestDispatcher rd = req.getRequestDispatcher("/cart.jsp");
		rd.forward(req, resp);
	}
}
