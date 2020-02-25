package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewModifyAd extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer adId = Integer.valueOf(req.getParameter("adIdModify"));
		
		Ad ad = DBManager.getInstance().getAdDao().findByPrimaryKey(adId);
		
		req.setAttribute("ad", ad);
		
		RequestDispatcher rd = req.getRequestDispatcher("/modifyAd.jsp");
		rd.forward(req, resp);
	}

}
