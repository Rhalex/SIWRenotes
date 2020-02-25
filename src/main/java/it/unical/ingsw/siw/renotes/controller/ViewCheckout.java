package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class ViewCheckout extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("userSession");
		
		if(user == null)
		{
			RequestDispatcher rd = req.getRequestDispatcher("/notLogin.html");
			rd.forward(req, resp);
		}
		else
		{
			Cart cart = user.getCart();
			//SE SONO PRESENTI BUNDLE CON INSERZIONI CONDIVISE VERRÀ NOTIFICATO ALL'UTENTE
			//INVITANDOLO AD OPERARE UNA SCELTA
			Set<Bundle> bundlesComuni = new HashSet<Bundle>();
			
			for(Bundle bd1 : cart.getBundles())
				for(Bundle bd2 : cart.getBundles())
					if(bd1.getBundle_id() != bd2.getBundle_id())
						for(Ad ad : bd1.getAds())
							if(bd2.getAds().contains(ad))
								bundlesComuni.add(bd2);
			
			if(bundlesComuni.size()>0)
			{
				req.setAttribute("errorBundles", bundlesComuni);
				
				RequestDispatcher rd = req.getRequestDispatcher("/alertBundlesCheckout.jsp");
				rd.forward(req, resp);
			}
			else
			{
				req.setAttribute("cart", cart);
				List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
				
				for(PaymentMethod pmTemp: user.getPaymentMethods())
				{
					PaymentMethod pm = new PaymentMethod();
					pm = DBManager.getInstance().getPaymentMethodDao().findByPrimaryKey(pmTemp.getCardNumber());
					
					paymentMethods.add(pm);
				}
				
				user.setPaymentMethods(paymentMethods);
				
				req.setAttribute("payments", user.getPaymentMethods());
				
				RequestDispatcher rd = req.getRequestDispatcher("/checkout.jsp");
				rd.forward(req, resp);
			}
		}
	}

}
