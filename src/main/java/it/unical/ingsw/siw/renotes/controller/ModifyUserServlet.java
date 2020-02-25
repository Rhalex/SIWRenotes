package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;
import it.unical.ingsw.siw.renotes.utility.PasswordManager;


public class ModifyUserServlet extends HttpServlet {
	
public ModifyUserServlet() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("userSession");
		
		DBManager.getInstance().getUserDao().modifyUserPwd(user, PasswordManager.getMD5(request.getParameter("password")));
		EmailManager.passwordModified(user);

		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}

}
