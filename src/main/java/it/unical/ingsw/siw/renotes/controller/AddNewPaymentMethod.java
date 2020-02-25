package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.PaymentMethod;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class AddNewPaymentMethod extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String owner = req.getParameter("owner");
		String cardNumber = req.getParameter("cardNumber");
		String cvc = req.getParameter("cvc");
		String expiryMonth = req.getParameter("expiryMonth");
		String expiryYear= req.getParameter("expiryYear");
		
		if(cardNumber.length()!=16 || cvc.length()!=3)
		{
			RequestDispatcher rd = req.getRequestDispatcher("/errorAddingPaymentMethod.html");
			rd.forward(req, resp);
		}
		else
		{
			PaymentMethod newPaymentMethod = new PaymentMethod();
			newPaymentMethod.setCardNumber(cardNumber);
			newPaymentMethod.setOwner(owner);
			newPaymentMethod.setCvc(Integer.valueOf(cvc));
			newPaymentMethod.setBalance(500);
			newPaymentMethod.setExpiryDate(new Date());
			newPaymentMethod.setDefault(false);
			
			User user = (User) req.getSession().getAttribute("userSession");
			
			List<PaymentMethod> pms = user.getPaymentMethods();
			
			if(pms.isEmpty())
				newPaymentMethod.setDefault(true);
			
			DBManager.getInstance().getPaymentMethodDao().save(newPaymentMethod);
			DBManager.getInstance().getUserDao().insertPaymentMethods(user, cardNumber);
				
			user.getPaymentMethods().add(newPaymentMethod);
			
			RequestDispatcher rd = req.getRequestDispatcher("ViewCheckout");
			rd.forward(req, resp);
		}
	
	}
}
