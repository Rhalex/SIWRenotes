package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;


public class ModifyUserImage extends HttpServlet {
	
	 public ModifyUserImage() { super(); }
	    
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			
		}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
			HttpSession session = request.getSession();
			//User user = DBManager.getInstance().getUserDao().findByMail((String)session.getAttribute("Mail"));
			User user = (User) session.getAttribute("userSessione");
			
			DBManager.getInstance().getUserDao().modifyImage(user, "webImg/avatar.png");
			
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		}

}
