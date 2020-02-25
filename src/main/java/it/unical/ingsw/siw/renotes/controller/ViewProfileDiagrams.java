package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Review;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewProfileDiagrams extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("userSession");
		
		//RECUPERO LE INSERZIONI CHE HA MESSO IN VENDITA
		List<Ad> adsManaged = DBManager.getInstance().getUserDao().findManagedAd(user);
		List<Double> valueReviews = new ArrayList<Double>();
		
		//PER OGNI INSERZIONE SOMMO I PARAMETRI DI UNA SINGOLA VALUTAZIONE E NE FACCIO LA MEDIA.
		//AGGIUNGO QUESTA MEDIA IN UNA LISTA DI VALUTAZIONI
		for(Ad ad: adsManaged)
		{	
			List<Review> reviewTemp =  DBManager.getInstance().getAdDao().findReview(ad.getId());
			
			for(Review rev: reviewTemp)
			{
				double media = (rev.getQuality() + rev.getReliability() + rev.getCompleteness())/3;
				valueReviews.add(media);
			}
		}
		
		ArrayList<HashMap<String, String> > data = new ArrayList<HashMap<String,String>>();
		int rate[] = {0,0,0,0,0};
		
		for(Double value: valueReviews)
		{
			double db = value;
			int index = (int) db -1;
			rate[index]++;
		}
		
		for(int i=0; i<5; i++)
		{
			HashMap<String, String> temp = new HashMap<String, String>();
			if(i+1>1)
				temp.put("label", String.valueOf(i+1) + " Stelle");
			else
				temp.put("label", String.valueOf(i+1) + " Stella");
			temp.put("value", String.valueOf(rate[i]));
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
