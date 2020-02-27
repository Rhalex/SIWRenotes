package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class Autocomplete extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		
		String title = req.getParameter("titolo");
		String subject = req.getParameter("materia");
		String university = req.getParameter("universita");
		String degreeCourse = req.getParameter("corsoDiLaurea");
		
		JsonArray arrayObj = new JsonArray();
		
		List<Ad> ads = new ArrayList<Ad>();
		List<Bundle> bundles = new ArrayList<Bundle>();
		
		User user = null;
		if(req.getSession().getAttribute("userSession") == null)
		{
			ads = DBManager.getInstance().getAdDao().findAllNoSession();
			bundles = DBManager.getInstance().getBundleDao().findAllNoSession();
		}
		else
		{
			user = (User) req.getSession().getAttribute("userSession");
			ads = DBManager.getInstance().getAdDao().findAll(user);
			bundles = DBManager.getInstance().getBundleDao().findAll(user);
		}
		
		ArrayList<String> data = new ArrayList<String>();
		
		if(title != null)
		{
			title.toString().toLowerCase();
			for (Ad ad: ads) {
				String temp = ad.getTitle().toLowerCase();
				if (temp.contains(title)) {
					arrayObj.add(ad.getTitle());
				}
			}
			
			for(Bundle bd : bundles)
			{
				String temp = bd.getTitle().toLowerCase();
				if (temp.contains(title)) {
					arrayObj.add(bd.getTitle());
				}
			}
		}
		
		if(subject != null)
		{
			subject.toString().toLowerCase();
			Set<String> sub = new HashSet<String>();
			for (Ad ad: ads)
				sub.add(ad.getSubject());
			
			List<String> s = new ArrayList<String>(sub);
			
			for (String subj: s) {
				String temp = subj.toLowerCase();
				if (temp.contains(subject)) {
					arrayObj.add(temp);
				}
			}
		}
		
		if(university != null)
		{
			university.toString().toLowerCase();
			Set<String> uni = new HashSet<String>();
			for (Ad ad: ads)
				uni.add(ad.getUniversity());
			
			List<String> s = new ArrayList<String>(uni);
			
			for (String univ: s) {
				String temp = univ.toLowerCase();
				if (temp.contains(university)) {
					arrayObj.add(temp);
				}
			}
		}
		
		if(degreeCourse != null)
		{
			degreeCourse.toString().toLowerCase();
			Set<String> dc = new HashSet<String>();
			for (Ad ad: ads)
				dc.add(ad.getDegreeCourse());
			
			List<String> s = new ArrayList<String>(dc);
			
			for (String dgc: s) {
				String temp = dgc.toLowerCase();
				if (temp.contains(degreeCourse)) {
					arrayObj.add(temp);
				}
			}
		}
		out.println(arrayObj.toString());
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
