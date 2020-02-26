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
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class CreateBundle extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		List<Ad> adsTemp = DBManager.getInstance().getUserDao().findManagedAd(user);
		List<Ad> ads = new ArrayList<Ad>();
		for(Ad ad : adsTemp)
		{
			Ad temp = new Ad();
			temp = DBManager.getInstance().getAdDao().findByPrimaryKey(ad.getId());
			ads.add(temp);
		}
		
		
		if(ads.size()>0)
			req.setAttribute("ads", ads);
		
		RequestDispatcher rd = req.getRequestDispatcher("/createBundle.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		String title = req.getParameter("titolo");
		String cont = req.getParameter("adIdMax");
		
		List<Ad> ads = new ArrayList<Ad>();
	
		
		for(int i=0; i<Integer.valueOf(cont); i++)
		{
			String temp = req.getParameter(String.valueOf(i+1));
			if(temp != null)
			{
				Ad ad = new Ad();
				ad.setId(Integer.valueOf(temp));
				ads.add(ad);
			}
		}
		
		Double price = Double.valueOf(req.getParameter("prezzo"));
		
		Bundle bundle = new Bundle();
		bundle.setAds(ads);
		bundle.setPrice(price);
		bundle.setTitle(title);
		bundle.setUser(user);
		
		DBManager.getInstance().getBundleDao().save(bundle);
		
		RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
		rd.forward(req, resp);
		
		
	}
}
