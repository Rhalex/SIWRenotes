package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;


public class TwoFactorAutenticationServlet extends HttpServlet {

	 public TwoFactorAutenticationServlet() { super(); }
	 	@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			request.getRequestDispatcher("ViewAdList").forward(request, response);
		}
	 	
	 	@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			HttpSession session = request.getSession();
			String code = (String) request.getParameter("verification-code");
			User utente = DBManager.getInstance().getUserDao().findByMail((String)session.getAttribute("Mail"));
			
			if(DBManager.getInstance().getUserDao().getVerificationCode(utente).equals(code))
			{
				doGet(request, response);
			}
			else
			{
				request.getRequestDispatcher("/twofactor.jsp").forward(request, response);
			}
		}
}
