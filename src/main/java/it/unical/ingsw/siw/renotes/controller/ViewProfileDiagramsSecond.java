package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewProfileDiagramsSecond extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("userSession");
		
		HashMap<Integer, Integer> ads = DBManager.getInstance().getUserDao().findPopularAds(user);
		
		List<Ad> adsM = DBManager.getInstance().getUserDao().findManagedAd(user);
	
		ArrayList<HashMap<String, String> > data = new ArrayList<HashMap<String,String>>();
		
		for(Map.Entry<Integer, Integer> h : ads.entrySet())
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			for(Ad ad: adsM)
				if(ad.getId() == h.getKey())
				{
					temp.put("label", ad.getTitle());
					temp.put("value", String.valueOf(h.getValue()));
				}
			data.add(temp);
		}
	
		Gson gson = new Gson();
		String json = gson.toJson(data);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		HashMap<Integer, Integer> ads = DBManager.getInstance().getUserDao().findPopularAds(user);
		
		List<Ad> adsM = DBManager.getInstance().getUserDao().findManagedAd(user);
	
		ArrayList<HashMap<String, String> > data = new ArrayList<HashMap<String,String>>();
		
		for(Map.Entry<Integer, Integer> h : ads.entrySet())
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			for(Ad ad: adsM)
				if(ad.getId() == h.getKey())
				{
					temp.put("label", ad.getTitle());
					temp.put("value", String.valueOf(h.getValue()*ad.getPrice()));
				}
			data.add(temp);
		}
	
		Gson gson = new Gson();
		String json = gson.toJson(data);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(json);
	}
}
