package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class RemoveMyBundle extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idS = req.getParameter("bundleId");
		
		if(idS!=null)
		{
			Integer id = Integer.valueOf(idS);
			
			Bundle bundle = new Bundle();
			bundle.setBundle_id(id);
			
			DBManager.getInstance().getBundleDao().delete(bundle);	
			
			RequestDispatcher rd = req.getRequestDispatcher("ViewMyBundle");
			rd.forward(req, resp);
		}
	}
}
