package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;

public class Checkout extends HttpServlet {
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("userSession");
		
		Cart cart = user.getCart();
		
		String paymentMethodSelected = req.getParameter("selectedMethod");
		
		PaymentMethod pmSelected = new PaymentMethod();
		
		for(PaymentMethod pm : user.getPaymentMethods())
			if(pm.getCardNumber().equals(paymentMethodSelected) )
				pmSelected = pm;
		
		if(pmSelected.getBalance() >= cart.getTotal())
		{
			
			for(Ad adB: cart.getAds())
			{
				DBManager.getInstance().getUserDao().insertBoughtAd(user, adB.getId());
				DBManager.getInstance().getCartDao().deleteAd(cart.getId(), adB.getId());	
			}
			for(Bundle bd : cart.getBundles())
			{
				for(Ad ad : bd.getAds())
					DBManager.getInstance().getUserDao().insertBoughtAd(user, ad.getId());	
				
				DBManager.getInstance().getCartDao().deleteBundle(cart.getId(), bd.getBundle_id());
			}
			
			pmSelected.setBalance(pmSelected.getBalance() - cart.getTotal());
			
			DBManager.getInstance().getPaymentMethodDao().update(pmSelected);
			
			//INVIO MAIL PER RECENSIONE
			for(Ad ad : cart.getAds())
			{
				EmailManager.sendReviewMail(user, ad);
				EmailManager.sendVendorMail(ad);
			}
			
			for(Bundle bd : cart.getBundles())
				for(Ad ad : bd.getAds())
				{
					EmailManager.sendReviewMail(user, ad);
					EmailManager.sendVendorMail(ad);
				}
			
			cart.clearCart();
			
			DBManager.getInstance().getCartDao().update(cart);
			user.setCart(cart);	
			
			RequestDispatcher rd = req.getRequestDispatcher("/checkoutSuccess.html");
			rd.forward(req, resp);
			
		}
		else
		{
			
			RequestDispatcher rd = req.getRequestDispatcher("/checkoutError.html");
			rd.forward(req, resp);
		}
		
		
	}

}
