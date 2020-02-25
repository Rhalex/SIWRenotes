package it.unical.ingsw.siw.renotes.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.model.Review;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class ViewAdList extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GET");
		String subject = req.getParameter("materia");
		String title = req.getParameter("titolo");
		String university = req.getParameter("universita");
		String degreeCourse = req.getParameter("corsoDiLaurea");
		
		User user = null;
		
		List<Ad> adsAll = new ArrayList<Ad>();
		List<Bundle> bundlesAll = new ArrayList<Bundle>();
		
		HashMap<Integer, Integer> mapAdsMostReviewed = new HashMap<Integer, Integer>();
		List<Ad> adsMostReviewed = new ArrayList<Ad>();
		
		if(req.getSession().getAttribute("userSession") == null)
		{
			adsAll = DBManager.getInstance().getAdDao().findAllNoSession();
			
			HashMap<Integer, Integer> temp = DBManager.getInstance().getAdDao().findAdsMostReviewedNoSession();
			mapAdsMostReviewed = sortByValue(temp);
			
			bundlesAll = DBManager.getInstance().getBundleDao().findAllNoSession();
		}
		else
		{
			user = (User) req.getSession().getAttribute("userSession");
			adsAll = DBManager.getInstance().getAdDao().findAll(user);
			HashMap<Integer, Integer> temp = DBManager.getInstance().getAdDao().findAdsMostReviewed(user);
			mapAdsMostReviewed = sortByValue(temp);
			
			List<Bundle> bundleTemp = DBManager.getInstance().getBundleDao().findAll(user);
			List<Ad> adBougth = DBManager.getInstance().getUserDao().findBoughtAd(user);
			Set<Bundle> remove = new HashSet<Bundle>();
			
			for(Bundle bundle : bundleTemp)
				for(Ad ad : bundle.getAds())
					if(adBougth.contains(ad))
						remove.add(bundle);
			
			for(Bundle bd : remove)
				if(bundleTemp.contains(bd))
					bundleTemp.remove(bd);
						
			bundlesAll = bundleTemp;
		}
		
		Cart cart = null;
		
		if(user == null && req.getSession().getAttribute("cartSession") == null)
		{
			cart = new Cart();
			cart.setId(0);
			cart.setTotal(0);
			cart.setDate(new Date(cart.getDate().getTime()));
			cart.setAds(new ArrayList<Ad>());
			cart.setBundles(new ArrayList<Bundle>());
			
			req.getSession().setAttribute("cartSession", cart);
		}
		else if(user != null && req.getSession().getAttribute("cartSession") == null)
		 cart = user.getCart();
		
		else if(user == null && req.getSession().getAttribute("cartSession") != null)
			cart = (Cart) req.getSession().getAttribute("cartSession");
		
		else if(user != null && req.getSession().getAttribute("cartSession") != null)
		{
			cart = (Cart) req.getSession().getAttribute("cartSession");
			
			Set<Ad> adUser = new HashSet<Ad>(user.getCart().getAds());
			Set<Ad> adCart = new HashSet<Ad>(cart.getAds());
			
			adUser.addAll(adCart);
			
			List<Ad> adsUser = new ArrayList<Ad>(adUser);
			
			List<Ad> adManaged = DBManager.getInstance().getUserDao().findManagedAd(user);
			List<Ad> adBought = DBManager.getInstance().getUserDao().findBoughtAd(user);
			
			for(Ad ad: adManaged)
				adsUser.remove(ad);
			
			for(Ad ad: adBought)
				adsUser.remove(ad);
			
			user.getCart().setAds(adsUser);
			
			Set<Bundle> bundleUser = new HashSet<Bundle>(user.getCart().getBundles());
			Set<Bundle> bundleCart = new HashSet<Bundle>(cart.getBundles());
			
			bundleUser.addAll(bundleCart);
			
			List<Bundle> bundlesUser = new ArrayList<Bundle>(bundleUser);
			
			List<Bundle> myBundle = DBManager.getInstance().getUserDao().findMyBundle(user);
			
			for(Bundle bd : myBundle)
				bundlesUser.remove(bd);
			
			user.getCart().setBundles(bundlesUser);
			
			req.getSession().removeAttribute("cartSession");
			
		}
		
		//ELIMINO I BUNDLE PRESENTI GIÀ NEL CARRELLO
		for(Bundle bd : cart.getBundles())
			bundlesAll.remove(bd);
		
		//ELIMINAZIONE DI INSERZIONI GIÀ PRESENTI IN UN BUNDLE
		Set<Integer> adsIdSet = new HashSet<Integer>();
		
//		for(Bundle bd : bundlesAll)
//			for(Ad ad : bd.getAds())
//				adsIdSet.add(ad.getId());
//		System.out.println("SET: " + adsIdSet.toString());
		//ELIMINAZIONE DI INSERZIONI GIÀ PRESENTI IN UN BUNDLE CHE HO NEL CARRELLO
		for(Bundle bd : cart.getBundles())
			for(Ad ad : bd.getAds())
				adsIdSet.add(ad.getId());
		
		List<Integer> adsId = new ArrayList<Integer>(adsIdSet);
		for(Integer id : adsId)
		{
			Ad temp = new Ad();
			temp.setId(id);
			
			adsAll.remove(temp);
		}
		
		Set<Ad> adSetTitle = new HashSet<Ad>();
		Set<Ad> adSetSubject = new HashSet<Ad>();
		Set<Ad> adSetUniversity = new HashSet<Ad>();
		Set<Ad> adSetDegreeCourse = new HashSet<Ad>();
	
		for(Ad ad : adsAll)
		{
			ad.setValue(DBManager.getInstance().getAdDao().findReview(ad.getId()));
			
			if( title!=null && !title.equals("") && ad.getTitle().toLowerCase().matches("(.*)" + title.toLowerCase() +"(.*)") )
				adSetTitle.add(ad);
			
			if(subject!=null && ad.getSubject().toLowerCase().equals(subject.toLowerCase()))
				adSetSubject.add(ad);
			
			if(university!=null && ad.getUniversity().toLowerCase().equals(university.toLowerCase()))
				adSetUniversity.add(ad);
			
			if(degreeCourse!=null && ad.getDegreeCourse().toLowerCase().equals(degreeCourse.toLowerCase()))
				adSetDegreeCourse.add(ad);
		}
		
		Set<Ad> adSetIntersezione = null;
		
		if(adSetTitle.size()>0)
			adSetIntersezione = new HashSet(adSetTitle);
		
		else if(adSetSubject.size()>0)
			adSetIntersezione = new HashSet(adSetSubject);
		
		else if(adSetUniversity.size()>0)
			adSetIntersezione = new HashSet(adSetUniversity);
		
		else if(adSetDegreeCourse.size()>0)
			adSetIntersezione = new HashSet(adSetDegreeCourse);
		
		if(adSetIntersezione != null)
		{
			List<Set> sets = new ArrayList<Set>();
			
			sets.add(adSetTitle);
			sets.add(adSetSubject);
			sets.add(adSetUniversity);
			sets.add(adSetDegreeCourse);
			
			for(Set set: sets)
				if(set.size()>0)
					adSetIntersezione.retainAll(set);
			
			List<Ad> ads = new ArrayList<Ad>(adSetIntersezione);
			
			for(Ad ad: cart.getAds())
					ads.remove(ad);
			
			Collections.sort(ads, Collections.reverseOrder());
			
			if(ads.size()>0)
				req.setAttribute("ads", ads);
	
		}
		
		boolean search = false;
		
		if(title!=null || subject!=null || university!=null || degreeCourse!=null)
			search = true;
			
		req.setAttribute("search", search);
		
		if(mapAdsMostReviewed.size()>0)
		{
			for(Map.Entry<Integer, Integer> h : mapAdsMostReviewed.entrySet())
			{
				Ad temp = new Ad();
				temp = DBManager.getInstance().getAdDao().findByPrimaryKey(h.getKey());
				
				List<Review> reviewTemp =  DBManager.getInstance().getAdDao().findReview(h.getKey());
				
				temp.setValue(reviewTemp);
				adsMostReviewed.add(temp);
			}
			
			for(Ad ad : cart.getAds())
				adsMostReviewed.remove(ad);
			
			for(Bundle bd : cart.getBundles())
				for(Ad ad : bd.getAds())
					adsMostReviewed.remove(ad);
			
			if(adsMostReviewed.size()>0)
				req.setAttribute("adsMR", adsMostReviewed);
			
			
		}
		//SELEZIONIAMO LE 6 INSERZIONI CON RATE MIGLIORE
		if(adsAll.size()>0)
		{
			ArrayList<Ad> valueAds = new ArrayList<Ad>();
				
			Collections.sort(adsAll, new Comparator<Ad>() {

				public int compare(Ad o1, Ad o2) {
					Integer value1 = o1.getValue();
					Integer value2 = o2.getValue();
					return value2.compareTo(value1);
				}
			});
			
			int index = 6;
			if(adsAll.size()<6)
				index = adsAll.size();
			
			for(int i=0; i<index; i++)
				valueAds.add(adsAll.get(i));
			
			for(Ad ad : cart.getAds())
				valueAds.remove(ad);
			
			for(Bundle bd : cart.getBundles())
				for(Ad ad : bd.getAds())
					valueAds.remove(ad);
			
			if(valueAds.size()>0)
				req.setAttribute("valueAds", valueAds);
			
			
		}
		
		if(bundlesAll.size()>0)
		{
			Set<Bundle> searchedBundles = new HashSet<Bundle>();
			
			for(Bundle bundle : bundlesAll)
				if(title!=null && !title.equals("") && bundle.getTitle().toLowerCase().matches("(.*)" + title.toLowerCase() +"(.*)"))
					searchedBundles.add(bundle);
			
			searchedBundles.retainAll(bundlesAll);
			
			//BUNDLE VISUALIZZABILI IN SEGUITO AD UNA RICERCA
			if(searchedBundles.size()>0)
				req.setAttribute("searchedBundles", searchedBundles);
			
			
			//BUNDLE VISIBILI PER LA SEZIONE BUNDLE NELLA HOME
			req.setAttribute("bundles", bundlesAll);
			
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("/adListNUOVO.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);

	}
	
	private static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, Integer> > list = 
               new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() { 
            public int compare(Map.Entry<Integer, Integer> o1,  
                               Map.Entry<Integer, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>(); 
        for (Map.Entry<Integer, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
}
