package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Preview;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class CreateNewAdvertisement extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("Titolo");
		String subject = req.getParameter("Materia");
		String cdl = req.getParameter("CdL");
		String university = req.getParameter("Universita");
		String description = req.getParameter("descrizione");
		Double price = Double.valueOf(req.getParameter("Prezzo"));
		String pathFile = req.getParameter("File");
		String pathPreview = req.getParameter("Anteprima");
		
		
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
		
		Ad ad = new Ad();
		ad.setTitle(title);
		ad.setSubject(subject);
		ad.setDegreeCourse(cdl);
		ad.setUniversity(university);
		ad.setDescription(description);
		ad.setPrice(price);
		ad.setFile(pathFile);
		ad.setPreview(preview);
		
		DBManager.getInstance().getAdDao().save(ad);
		
		User user = (User) req.getSession().getAttribute("userSession");
		
		DBManager.getInstance().getUserDao().insertManagedAd(user, DBManager.getInstance().getAdDao().lastSerialAdId());
		
		RequestDispatcher rd = req.getRequestDispatcher("/addeddAdSuccessfully.html");
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
