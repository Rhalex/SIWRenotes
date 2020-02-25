package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;
import it.unical.ingsw.siw.renotes.utility.PasswordManager;


public class GoogleLogin extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		
		JsonElement json = new Gson().fromJson(request.getReader(), JsonElement.class);
		String mail = json.toString();
		mail = mail.substring(1, mail.length() - 1);
		
		User user = DBManager.getInstance().getUserDao().findByMail(mail);
		if(user == null)
		{
			user = new User();
			user.setMail(mail);
			user.setUsername(mail);
			user.setPassword(PasswordManager.getMD5("default"));
			Cart cart = new Cart();
			cart.setId(DBManager.getInstance().getCartDao().lastSerialCartId()+1);
			
			DBManager.getInstance().getCartDao().save(cart);
			user.setCart(cart);
			
			DBManager.getInstance().getUserDao().save(user);
			
			PaymentMethod pmMaster = DBManager.getInstance().getPaymentMethodDao().findByPrimaryKey("0000000000000010");
			List<PaymentMethod> pms = new ArrayList<PaymentMethod>();
			pms.add(pmMaster);
			
			DBManager.getInstance().getUserDao().insertPaymentMethods(user, pmMaster.getCardNumber());
			
			user.setPaymentMethods(pms);
			
			EmailManager.registerValidation(user);
			HttpSession session = request.getSession();
			//session.setAttribute("Mail", mail);
			//session.setAttribute("Nome", user.getUsername());
			
			session.setAttribute("userSession", user);
			request.getRequestDispatcher("adList.jsp").forward(request, response);
				
		}
		else
		{
			HttpSession session = request.getSession();
			Cart cart = user.getCart();
			cart.setAds(DBManager.getInstance().getCartDao().listOfAds(cart));
			cart.setTotal(cart.getTotal());
			DBManager.getInstance().getCartDao().update(cart);
			
			user.setCart(cart);
			List<PaymentMethod> paymentMethods = DBManager.getInstance().getUserDao().findPaymentMethods(user); //HA SOLO I NUMERI DI CARTA
			
			user.setPaymentMethods(paymentMethods);
			
			session.setAttribute("userSession", user);
//			session.setAttribute("Mail", mail);
//			session.setAttribute("Nome", user.getUsername());
			session.setAttribute("userSession", user);
			request.getRequestDispatcher("adList.jsp").forward(request, response);
		}
		
	}
}
