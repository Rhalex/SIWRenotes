package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Review;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewSummaryProfile extends HttpServlet {

	private User user = new User();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("u");
		
		user = DBManager.getInstance().getUserDao().findByUsername(username);
		
		req.setAttribute("user", user);
		RequestDispatcher rd = req.getRequestDispatcher("/summaryProfile.jsp");
		rd.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//User user = (User) req.getSession().getAttribute("userSession");
		System.out.println(user.getUsername());
		//RECUPERO LE INSERZIONI CHE HA MESSO IN VENDITA
		List<Ad> adsManaged = DBManager.getInstance().getUserDao().findManagedAd(user);
		List<Double> valueReviews = new ArrayList<Double>();
		
		//PER OGNI INSERZIONE CALCOLO IL VALORE
		for(Ad ad: adsManaged)
		{	
			List<Review> reviewTemp =  DBManager.getInstance().getAdDao().findReview(ad.getId());
			
			ad.setValue(reviewTemp);
		}
		
		ArrayList<HashMap<String, String> > data = new ArrayList<HashMap<String,String>>();
		
		for(Ad ad: adsManaged)
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			
			temp.put("label", ad.getTitle());
			temp.put("value", String.valueOf(ad.getValue()));
			data.add(temp);	
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(data);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
	}

}
