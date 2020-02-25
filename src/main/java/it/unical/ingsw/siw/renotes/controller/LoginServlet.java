package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;
import it.unical.ingsw.siw.renotes.utility.PasswordManager;


public class LoginServlet extends HttpServlet {

public LoginServlet() { super(); }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//request.getRequestDispatcher("twofactor.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String mail = (String) request.getParameter("mail-accesso");
		String password = (String) request.getParameter("password-accesso");
		User utente = DBManager.getInstance().getUserDao().findByMail(mail);
		if(utente != null && utente.getMail().equals(mail))
		{
			if(PasswordManager.getMD5(password).equals(utente.getPassword()))
			{
				HttpSession session = request.getSession();
				session.setAttribute("Nome", utente.getUsername());
				session.setAttribute("Mail", utente.getMail());
				
				//AGGIUNTO DA ALE
				Cart cart = utente.getCart();
				cart.setAds(DBManager.getInstance().getCartDao().listOfAds(cart));
				cart.setBundles(DBManager.getInstance().getCartDao().listOfBundles(cart));
				cart.setTotal(cart.getTotal());
				DBManager.getInstance().getCartDao().update(cart);
				
				utente.setCart(cart);
				List<PaymentMethod> paymentMethods = DBManager.getInstance().getUserDao().findPaymentMethods(utente); //HA SOLO I NUMERI DI CARTA
				
				utente.setPaymentMethods(paymentMethods);
				
				session.setAttribute("userSession", utente);
				//
				request.setAttribute("mail", utente.getMail());
				DBManager.getInstance().getUserDao().setVerificationCode(utente, Integer.toString(EmailManager.sendTwoFactorAutenticationCode(utente)));
				
				RequestDispatcher rd = request.getRequestDispatcher("twofactor.jsp");
				rd.forward(request, response);
			}
			else
			{
				request.setAttribute("scrittaErrore", "Mail o password errate");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
		else
		{
			request.setAttribute("scrittaErrore", "Mail o password errate");
			request.getRequestDispatcher("index.jsp").forward(request, response);;
		}
	}
}
