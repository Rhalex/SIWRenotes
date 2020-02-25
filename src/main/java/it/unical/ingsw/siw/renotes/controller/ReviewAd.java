package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Review;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;

public class ReviewAd extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer quality = Integer.valueOf(req.getParameter("qualityValue"));
		Integer reliability = Integer.valueOf(req.getParameter("reliabilityValue"));
		Integer completeness = Integer.valueOf(req.getParameter("completenessValue"));
		String comment = req.getParameter("comment");
		
		
		String idS = req.getParameter("adIdLink");
		Integer id = Integer.valueOf(idS);
		Ad ad = new Ad();
		ad.setId(id);
		ad.setTitle(DBManager.getInstance().getAdDao().findByPrimaryKey(id).getTitle());
		
		String username = req.getParameter("userLink");
		User user = DBManager.getInstance().getUserDao().findByUsername(username);
		
		Review review = new Review();
		review.setQuality(quality);
		review.setReliability(reliability);
		review.setCompleteness(completeness);
		review.setComment(comment);
		review.setAd(ad);
		review.setUser(user);
		
		DBManager.getInstance().getReviewDao().save(review);
		
		EmailManager.sendVendorReviewMail(ad);
		
		RequestDispatcher rd = req.getRequestDispatcher("/reviewAddedSuccessfully.html");
		rd.forward(req, resp);
			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String idS = req.getParameter("adIdLink");

		Integer id = Integer.valueOf(idS);
		
		String adTitle = DBManager.getInstance().getAdDao().findByPrimaryKey(id).getTitle();
		
		req.setAttribute("adTitle", adTitle);
		
		RequestDispatcher rd = req.getRequestDispatcher("/reviewAdd.jsp");
		rd.forward(req, resp);
		
	}

}
