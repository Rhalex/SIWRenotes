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

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;
import it.unical.ingsw.siw.renotes.utility.PasswordManager;

public class FacebookLogin extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JsonElement json = new Gson().fromJson(req.getReader(), JsonElement.class);
		String mail = json.toString();
		mail = mail.substring(1, mail.length() - 1);
		
		User user = DBManager.getInstance().getUserDao().findByMail(mail);
		if(user == null)
		{
			user = new User();
			user.setMail(mail);
			user.setUsername(mail);
			user.setPassword(PasswordManager.getMD5("fb"));
			Cart cart = new Cart();
			cart.setId(DBManager.getInstance().getCartDao().lastSerialCartId()+1);
			cart.setAds(new ArrayList<Ad>());
			cart.setBundles(new ArrayList<Bundle>());
			DBManager.getInstance().getCartDao().save(cart);
			user.setCart(cart);
			
			DBManager.getInstance().getUserDao().save(user);
			
			PaymentMethod pmMaster = DBManager.getInstance().getPaymentMethodDao().findByPrimaryKey("0000000000000010");
			List<PaymentMethod> pms = new ArrayList<PaymentMethod>();
			pms.add(pmMaster);
			
			DBManager.getInstance().getUserDao().insertPaymentMethods(user, pmMaster.getCardNumber());
			
			user.setPaymentMethods(pms);
			
			EmailManager.registerValidation(user);
			HttpSession session = req.getSession();
			
			session.setAttribute("userSession", user);
			//req.getRequestDispatcher("ViewAdList").forward(req, resp);
				
		}
		else
		{
			HttpSession session = req.getSession();
			Cart cart = user.getCart();
			cart.setAds(DBManager.getInstance().getCartDao().listOfAds(cart));
			cart.setBundles(DBManager.getInstance().getCartDao().listOfBundles(cart));
			cart.setTotal(cart.getTotal());
			DBManager.getInstance().getCartDao().update(cart);
			
			user.setCart(cart);
			List<PaymentMethod> paymentMethods = DBManager.getInstance().getUserDao().findPaymentMethods(user); //HA SOLO I NUMERI DI CARTA
			
			user.setPaymentMethods(paymentMethods);
			
			session.setAttribute("userSession", user);
			//req.getRequestDispatcher("ViewAdList").forward(req, resp);
		}
	}

}
