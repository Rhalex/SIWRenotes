package it.unical.ingsw.siw.renotes.persistance.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.BundleDao;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.persistance.dao.DataSource;

public class BundleDaoJDBC implements BundleDao{
	private DataSource dataSource;
	
	public BundleDaoJDBC (DataSource ds){
		dataSource = ds;
	}

	
	public void save(Bundle bundle) {
		Connection connection=null;
		try {
			connection= dataSource.getConnection();
			String insert="insert into bundle(titolo,user_username,user_mail,prezzo,inserzione1,inserzione2,inserzione3,inserzione4,inserzione5) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement stm=connection.prepareStatement(insert);
			stm.setString(1, bundle.getTitle());
			stm.setString(2, bundle.getUser().getUsername());
			stm.setString(3, bundle.getUser().getMail());
			stm.setDouble(4, bundle.getPrice());
			for(int i=0; i<5;i++) {
				if(i<bundle.getAds().size()) {
					stm.setInt(i+5, bundle.getAds().get(i).getId());
				}
				else
					stm.setNull(i+5, 0);
			}
			stm.executeUpdate();
			
		} catch (Exception e) {
			if(connection != null)
			{
				try {
					connection.rollback();
				} catch (SQLException e1) {
					
					throw new RuntimeException(e.getMessage());
				}
			}	
			
		}
		finally
		{
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	public Bundle findByPrimaryKey(Integer id) {
		Connection connection=null;
		Bundle bundle=null;
		List<Integer> adsId = new ArrayList<Integer>();
		
		try {
			connection=dataSource.getConnection();
			String query="select * from bundle where bundle_id=?";
			
			PreparedStatement stm=connection.prepareStatement(query);
			stm.setInt(1, id);
			
			ResultSet result = stm.executeQuery();
			
			if(result.next()) {
				bundle=new Bundle();
				bundle.setBundle_id(result.getInt("bundle_id"));
				bundle.setTitle(result.getString("titolo"));
				bundle.setPrice(result.getDouble("prezzo"));
				bundle.setImage(result.getString("anteprima"));
				
				User user = new User();
				user.setUsername(result.getString("user_username"));
				user.setMail(result.getString("user_mail"));
				
				bundle.setUser(user);
				
				
				for(int i=0; i<5; i++)
				{
					Integer val = result.getInt("inserzione" + String.valueOf((i+1)));
					
					if( val != 0)
						adsId.add(val);
				}
			}
			
		} catch (Exception e) {
			if(connection != null)
			{
				try {
					connection.rollback();
				} catch (SQLException e1) {
					
					throw new RuntimeException(e.getMessage());
				}
			}	
		}
		finally
		{
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		AdDaoJDBC adDao = new AdDaoJDBC(dataSource);
		List<Ad> ads = new ArrayList<Ad>();
		
		for(Integer adId: adsId)
		{
			ads.add(DBManager.getInstance().getAdDao().findByPrimaryKey(adId));	
		}
		
		bundle.setAds(ads);
		
		return bundle;
	}

	public void delete(Bundle bundle) {
			Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String delete = "delete from bundle where bundle_id = ? ";
			PreparedStatement stm = connection.prepareStatement(delete);
			
			stm.setInt(1, bundle.getBundle_id());
			
			stm.executeUpdate();
			
		} 
		catch (SQLException e) {
			if(connection != null)
			{
				try {
					connection.rollback();
				} catch (SQLException e1) {
					
					throw new RuntimeException(e.getMessage());
				}
			}	
		}
		finally
		{
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}


	public void insertAd(Bundle bundle, Ad ad, String index) {
		
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String insert = "update bundle SET inserzione"+index+"=? where bundle_id=?";
			
			PreparedStatement stm = connection.prepareStatement(insert);
			
			stm.setInt(1, ad.getId());
			stm.setInt(2, bundle.getBundle_id());
			
			stm.executeUpdate();
		} 
		catch (SQLException e) {
			if(connection != null)
			{
				try {
					connection.rollback();
				} catch (SQLException e1) {
					
					throw new RuntimeException(e.getMessage());
				}
			}	
		}
		finally
		{
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	
	}


	public List<Bundle> findAllNoSession() {
		
		List<Bundle> bundles = new ArrayList<Bundle>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String select = "select * from bundle";
			
			PreparedStatement stm = connection.prepareStatement(select);
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Bundle bundle=new Bundle();
				bundle.setBundle_id(result.getInt("bundle_id"));
				bundle.setTitle(result.getString("titolo"));
				bundle.setPrice(result.getDouble("prezzo"));
				bundle.setImage(result.getString("anteprima"));
				
				User user = new User();
				user.setUsername(result.getString("user_username"));
				user.setMail(result.getString("user_mail"));
				
				bundle.setUser(user);
				
				List<Ad> adsId = new ArrayList<Ad>();
				
				for(int i=0; i<5; i++)
				{
					Integer val = result.getInt("inserzione" + String.valueOf((i+1)));
					
					if( val != 0)
					{
						Ad ad = new Ad();
						ad.setId(val);
						adsId.add(ad);
					}
				}
				
				bundle.setAds(adsId);
				
				bundles.add(bundle);
			}
		} 
		catch (SQLException e) {
			if(connection != null)
			{
				try {
					connection.rollback();
				} catch (SQLException e1) {
					
					throw new RuntimeException(e.getMessage());
				}
			}	
		}
		finally
		{
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
//		for(Bundle bundle : bundles)
//			for(Ad ad : bundle.getAds())
//				ad = DBManager.getInstance().getAdDao().findByPrimaryKey(ad.getId());
		
		return bundles;
	}


	public List<Bundle> findAll(User user) {
		List<Bundle> bundles = new ArrayList<Bundle>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String select = "select * from bundle where user_username !=? and user_mail != ?";
			
			PreparedStatement stm = connection.prepareStatement(select);
			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Bundle bundle=new Bundle();
				bundle.setBundle_id(result.getInt("bundle_id"));
				bundle.setTitle(result.getString("titolo"));
				bundle.setPrice(result.getDouble("prezzo"));
				bundle.setImage(result.getString("anteprima"));
				
				User ut = new User();
				ut.setUsername(result.getString("user_username"));
				ut.setMail(result.getString("user_mail"));
				
				bundle.setUser(ut);
				
				List<Ad> adsId = new ArrayList<Ad>();
				
				for(int i=0; i<5; i++)
				{
					Integer val = result.getInt("inserzione" + String.valueOf((i+1)));
					
					if( val != 0)
					{
						Ad ad = new Ad();
						ad.setId(val);
						adsId.add(ad);
					}
				}
				
				bundle.setAds(adsId);
				
				bundles.add(bundle);
			}
		} 
		catch (SQLException e) {
			if(connection != null)
			{
				try {
					connection.rollback();
				} catch (SQLException e1) {
					
					throw new RuntimeException(e.getMessage());
				}
			}	
		}
		finally
		{
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		return bundles;
	}
}
