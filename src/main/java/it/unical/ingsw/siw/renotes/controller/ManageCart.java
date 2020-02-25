package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ManageCart extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Cart cart = null;
		User user = null;

		if( req.getSession().getAttribute("userSession") == null)
		{
			cart = (Cart) req.getSession().getAttribute("cartSession");
		}
		 
		else
		{
			user = (User) req.getSession().getAttribute("userSession");
			cart = user.getCart();	
		}
		
		String adIdS = req.getParameter("adIdAdd");
		
		
		if(adIdS != null)
		{
			Integer adId = Integer.valueOf(adIdS);
			
			Ad newAd = new Ad();
			newAd = DBManager.getInstance().getAdDao().findByPrimaryKey(adId);
			
			boolean alreadyInsert = false;
			
			List<Ad> ads = cart.getAds();
			
			if(ads.size()>0)
				for(int i=0; i<ads.size() && !alreadyInsert; i++)
				{
					if(ads.get(i).getId() == adId)
						alreadyInsert = true;
				}
			
			if(alreadyInsert)
			{
				
				RequestDispatcher rd = req.getRequestDispatcher("/errorAddAd.html");
				rd.forward(req, resp);
			}
			else
			{
				if(user != null)
				{
					DBManager.getInstance().getCartDao().insertAd(cart.getId(), adId);
					
					cart.getAds().add(newAd);
					
					cart.setTotal(cart.getTotal());
					DBManager.getInstance().getCartDao().update(cart);
					
					user.setCart(cart);
				}
				else
				{
					cart.getAds().add(newAd);
					cart.setTotal(cart.getTotal());
				}
				
			}
		}
		
		String bundleIdS = req.getParameter("bundleIdAdd");
		
		if(bundleIdS != null)
		{
			Integer bundleId = Integer.valueOf(bundleIdS);
			
			Bundle bundle = new Bundle();
			
			bundle = DBManager.getInstance().getBundleDao().findByPrimaryKey(bundleId);
			
			if(user != null)
			{
				DBManager.getInstance().getCartDao().insertBundle(cart.getId(), bundleId);
				
				cart.getBundles().add(bundle);
				
				for(Ad ad : bundle.getAds())
				{
					DBManager.getInstance().getCartDao().deleteAd(cart.getId(), ad.getId());
					cart.getAds().remove(ad);
				}
				
				cart.setTotal(cart.getTotal());
				DBManager.getInstance().getCartDao().update(cart);
				
				user.setCart(cart);
			}
			else
			{
				cart.getBundles().add(bundle);
				cart.setTotal(cart.getTotal());
			}
			
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("ViewAdList");
		rd.forward(req, resp);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Cart cart = null;
		User user = null;

		if( req.getSession().getAttribute("userSession") == null)
		{
			cart = (Cart) req.getSession().getAttribute("cartSession");
		}
		 
		else
		{
			user = (User) req.getSession().getAttribute("userSession");
			cart = user.getCart();	
		}
		
		String idString = req.getParameter("adIdRemove");
		String bundleIdS = req.getParameter("bundleIdRemove");
		
		Integer id = 0;
		Integer bundleId = 0;
		if(idString != null)
		{
			id = Integer.valueOf(req.getParameter("adIdRemove"));
			
			Ad newAd = new Ad();
			newAd.setId(id);
			
			if(user != null)
				DBManager.getInstance().getCartDao().deleteAd(cart.getId(), newAd.getId());
			
			cart.getAds().remove(newAd);
			
			if(user != null)
				user.setCart(cart);	
		}
		
		if(bundleIdS != null)
		{
			bundleId = Integer.valueOf(req.getParameter("bundleIdRemove"));
			
			Bundle bundle = new Bundle();
			bundle.setBundle_id(bundleId);
			
			if(user != null)
				DBManager.getInstance().getCartDao().deleteBundle(cart.getId(), bundleId);
			
			cart.getBundles().remove(bundle);
			
			if(user != null)
				user.setCart(cart);	
			
		}
		else if(idString == null && bundleIdS == null) 
		{
			Integer loop = Integer.valueOf(req.getParameter("clearCart"));
			
			List<Ad> adsDaRimuovere = cart.getAds();
		
			int cont=0;
			
			if(user!=null)
			{
				while(cont<loop && !adsDaRimuovere.isEmpty())
				{
					Ad newAd = new Ad();
					newAd.setId(adsDaRimuovere.get(0).getId());
					
					DBManager.getInstance().getCartDao().deleteAd(cart.getId(), newAd.getId());
					
					adsDaRimuovere.remove(newAd);
					cont++;	
				}
			}
			
			
			List<Bundle> bundlesDaRimuovere = cart.getBundles();
			cont = 0;
			while(cont<loop && !bundlesDaRimuovere.isEmpty())
			{
				Bundle bundle = new Bundle();
				bundle.setBundle_id(bundlesDaRimuovere.get(0).getBundle_id());
				
				DBManager.getInstance().getCartDao().deleteBundle(cart.getId(), bundle.getBundle_id());
				
				bundlesDaRimuovere.remove(bundle);
				cont++;
			}
			
			cart.getAds().clear();
			cart.getBundles().clear();
			
		}
		
		cart.setTotal(cart.getTotal());
	
		if(user!=null)
		{
			DBManager.getInstance().getCartDao().update(cart);
			user.setCart(cart);
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("ViewCart");
		rd.forward(req, resp);
		
	}

}
