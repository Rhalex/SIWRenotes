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
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;


public class addMyPurchased extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		List<Ad> adsTemp = DBManager.getInstance().getUserDao().findBoughtAd(user);
		
		List<Ad> ads = new ArrayList<Ad>(); 
		
		for(Ad ad: adsTemp)
		{
			Ad a = DBManager.getInstance().getAdDao().findByPrimaryKey(ad.getId());
			ads.add(a);
		}
		
		if(ads.size()>0) 
			req.setAttribute("ads", ads);
		
		user.setBoughtAd(ads);
		
		RequestDispatcher rd = req.getRequestDispatcher("/purchased.jsp");
		rd.forward(req, resp);
	}
}
