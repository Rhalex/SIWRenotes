package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.persistance.dao.UserDao;
import it.unical.ingsw.siw.renotes.utility.EmailManager;
import it.unical.ingsw.siw.renotes.utility.PasswordManager;


public class RegisterServlet extends HttpServlet {

public RegisterServlet() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		UserDao utenteDao = DBManager.getInstance().getUserDao();
		if(utenteDao.findByMail(request.getParameter("mail-registrazione")) == null)
		{
			User utente = new User();
			utente.setMail(request.getParameter("mail-registrazione"));
			utente.setUsername(request.getParameter("username-registrazione"));
			utente.setPassword(PasswordManager.getMD5(request.getParameter("password-registrazione")));
			Cart cart = new Cart();
			cart.setId(DBManager.getInstance().getCartDao().lastSerialCartId()+1);
			
			DBManager.getInstance().getCartDao().save(cart);
			utente.setCart(cart);
			
			utenteDao.save(utente);
			
			PaymentMethod pmMaster = DBManager.getInstance().getPaymentMethodDao().findByPrimaryKey("0000000000000010");
			List<PaymentMethod> pms = new ArrayList<PaymentMethod>();
			pms.add(pmMaster);
			
			DBManager.getInstance().getUserDao().insertPaymentMethods(utente, pmMaster.getCardNumber());
			
			utente.setPaymentMethods(pms);
		
			
			EmailManager.registerValidation(utente);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
}
