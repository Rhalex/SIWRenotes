package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Preview;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.utility.EmailManager;


public class ModifyAdvertisement extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer adId = Integer.valueOf(req.getParameter("adId"));
		String title = req.getParameter("Titolo");
		String subject = req.getParameter("Materia");
		String cdl = req.getParameter("CdL");
		String university = req.getParameter("Universita");
		String description = req.getParameter("Descrizione");
		Double price = Double.valueOf(req.getParameter("Prezzo"));
		String pathFile = req.getParameter("File");
		String pathPreview = req.getParameter("Anteprima");
		String choiceVersion = req.getParameter("sceltaVersione");
		Double oldVersion = Double.valueOf(req.getParameter("versione"));
		
		int previewId = choose(pathFile);
		
		Preview preview = new Preview();
		preview.setId(previewId);
		
		if(previewId == 1)
		{
			preview.setImage("webImg/anteprimaTestuale.png");
			pathFile = "file/test.pdf";
		}
		else
		{
			preview.setImage("webImg/anteprima_video.png");
			pathFile = "file/video.mp4";
		}
		double newVersion = 1.0;
		
		if(choiceVersion.equals("1"))
		{
			newVersion = (int) (oldVersion*100/100.0);
			newVersion++;
			
		}
		else
		{
			double tronc = (int) (oldVersion*100/100.0);
			double decVal = oldVersion- tronc;
			
			if(decVal + 0.1 >= 1)
				newVersion = tronc+1;
			else
				newVersion = oldVersion + 0.1;
		}
			
		Ad ad = new Ad();
		ad.setId(adId);
		ad.setTitle(title);
		ad.setSubject(subject);
		ad.setDegreeCourse(cdl);
		ad.setUniversity(university);
		ad.setDescription(description);
		ad.setPrice(price);
		ad.setFile(pathFile);
		ad.setPreview(preview);
		ad.setConsultable(true);
		ad.setVersion(newVersion);
		
		DBManager.getInstance().getAdDao().update(ad);
		
		List<User> usersToNotify = DBManager.getInstance().getAdDao().findBuyer(ad);
		
		for(User user: usersToNotify)
			EmailManager.sendBuyerNotifyMail(user, ad);	
		
		RequestDispatcher rd = req.getRequestDispatcher("/addeddAdSuccessfully.html");
		rd.forward(req, resp);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Integer id = Integer.valueOf(req.getParameter("adIdRemove"));
		DBManager.getInstance().getAdDao().updateConsultable(id);
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		DBManager.getInstance().getUserDao().deleteManagedAd(user, id);
		
		
		RequestDispatcher rd = req.getRequestDispatcher("addMyAdvertisement");
		rd.forward(req, resp);
	}
	
	protected int choose(String path)
	{		
		int i=path.lastIndexOf('.');
				
		if(i>0 && i<path.length()-1)
		{
			if(path.substring(i+1).toLowerCase().equals("pdf"))
				return 1; //ANTEPRIMA TXT
				
			return 2; //ANTEPRIMA VIDEO
		}
		
		return 1;	
	}
}
