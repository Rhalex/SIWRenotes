package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Review;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class GetBundleInfo extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer bundleId = Integer.valueOf(req.getParameter("bundleId"));
		String viewButton = req.getParameter("viewButton");
		
		if(viewButton != null)
		{
			boolean b = true;
			
			req.setAttribute("viewButton", b);
		}
		
		Bundle bundle = DBManager.getInstance().getBundleDao().findByPrimaryKey(bundleId);
			
		if(bundle != null)
		{
			for(Ad ad: bundle.getAds())
			{
				List<Review> reviews = DBManager.getInstance().getAdDao().findReview(ad.getId());
				ad.setValue(reviews);
			}
			req.setAttribute("bundle", bundle);
			req.setAttribute("ads", bundle.getAds());
			
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("/bundleInfo.jsp");
		rd.forward(req, resp);
				
	}
}
