package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewMyBundle extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User user = (User) req.getSession().getAttribute("userSession");
				
		List<Bundle> bundles = DBManager.getInstance().getUserDao().findMyBundle(user);
		
		if(bundles.size()>0)
			req.setAttribute("bundles", bundles);
		
		RequestDispatcher rd = req.getRequestDispatcher("/myBundle.jsp");
		rd.forward(req, resp);
	}
		
}
