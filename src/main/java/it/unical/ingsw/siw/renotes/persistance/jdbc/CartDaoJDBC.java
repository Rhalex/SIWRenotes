package it.unical.ingsw.siw.renotes.persistance.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Cart;
import it.unical.ingsw.siw.renotes.persistance.dao.AdDao;
import it.unical.ingsw.siw.renotes.persistance.dao.CartDao;
import it.unical.ingsw.siw.renotes.persistance.dao.DataSource;

public class CartDaoJDBC implements CartDao {

	private DataSource dataSource;
	
	public CartDaoJDBC(DataSource ds) 
	{
		dataSource = ds;
	}
	
	public void save(Cart cart) {
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String insert = "insert into carrello(totale,data) values(?,?)";
			
			PreparedStatement stm = connection.prepareStatement(insert);
			
			stm.setDouble(1, cart.getTotal());
			stm.setDate(2, new Date(cart.getDate().getTime()));
			
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
	
	//NON VIENE SETTATA LA LISTA DI INSERZIONI
	public Cart findByPrimaryKey(Integer id) {
		Cart cart = null;
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String query = "select * from carrello where carrello_id=?";
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setInt(1, id);
			
			ResultSet result = stm.executeQuery();
			
			if(result.next())
			{
				cart = new Cart();
				cart.setId(result.getInt("carrello_id"));
				cart.setTotal(result.getDouble("totale"));
				cart.setDate(result.getDate("data"));
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
		
		//cart.setAds(listOfAds(cart));
		
		return cart;
	}

	public void update(Cart cart) {
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String update = "update carrello SET totale=?, data=? where carrello_id=?";
			PreparedStatement stm = connection.prepareStatement(update);
			
			stm.setDouble(1, cart.getTotal());
			stm.setDate(2, new Date(cart.getDate().getTime()));
			stm.setInt(3, cart.getId());
			
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
	
	//ESSENDO UNA RELAZIONE 1 AD 1 CON L'UTENTE LA DELETE HA SENSO QUANDO L'UTENTE NON ESISTE PIÙ
	public void delete(Cart cart) {
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String delete = "delete from carrello where carrello_id = ? ";
			PreparedStatement stm = connection.prepareStatement(delete);
			
			stm.setInt(1, cart.getId());
			
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

	public void insertAd(Integer cartId, Integer adId) {
		Connection  connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String insert = "insert into inserzioni_nel_carrello(inserzione,carrello) values(?,?)";
			PreparedStatement stm = connection.prepareStatement(insert);
			
			stm.setInt(1, adId);
			stm.setInt(2, cartId);
			
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

	public void deleteAd(Integer cartId, Integer adId) {
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String delete = "delete from inserzioni_nel_carrello where inserzione=? and carrello = ?";
			PreparedStatement stm = connection.prepareStatement(delete);
			
			stm.setInt(1, adId);
			stm.setInt(2, cartId);
			
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
	
	//LE ad ALL'INTERNO DELLA LISTA HANNO SOLO L'id ASSEGNATO
	public List<Ad> listOfAds(Cart cart) {
		
		List<Ad> adsTemp = new ArrayList<Ad>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String query = "select inserzione from inserzioni_nel_carrello where carrello=?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, cart.getId());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Ad ad = new Ad();
				
				ad.setId(result.getInt("inserzione"));
				
				adsTemp.add(ad);
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
		//MI SERVE PER IL CARRELLO
		List<Ad> ads = new ArrayList<Ad>();
		for(Ad ad: adsTemp)
		{
			AdDaoJDBC adDaoJDBC = new AdDaoJDBC(dataSource);
			Ad a = new Ad();
			a = adDaoJDBC.findByPrimaryKey(ad.getId());
			
			ads.add(a);
		}
		
		return ads;
	}
	
	public int lastSerialCartId() {
		Connection connection = null;
		int val = 0;
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement statement;
			String query = "select max(carrello_id) from carrello";
			statement = connection.prepareStatement(query);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
				val = result.getInt(1);
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		return val;
	}

	public void insertBundle(Integer cartId, Integer bundleId) {
		Connection  connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String insert = "insert into bundle_nel_carrello(bundle,carrello) values(?,?)";
			PreparedStatement stm = connection.prepareStatement(insert);
			
			stm.setInt(1, bundleId);
			stm.setInt(2, cartId);
			
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

	public void deleteBundle(Integer cartId, Integer bundleId) {
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String delete = "delete from bundle_nel_carrello where bundle=? and carrello = ?";
			PreparedStatement stm = connection.prepareStatement(delete);
			
			stm.setInt(1, bundleId);
			stm.setInt(2, cartId);
			
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

	public List<Bundle> listOfBundles(Cart cart) {
		List<Bundle> bundlesTemp = new ArrayList<Bundle>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String query = "select bundle from bundle_nel_carrello where carrello=?";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, cart.getId());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Bundle bundle = new Bundle();
				
				bundle.setBundle_id((result.getInt("bundle")));
				
				bundlesTemp.add(bundle);
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
		
		List<Bundle> bundles = new ArrayList<Bundle>();
		for(Bundle bd: bundlesTemp)
		{
			BundleDaoJDBC bundleDaoJDBC = new BundleDaoJDBC(dataSource);
			Bundle b = new Bundle();
			b = bundleDaoJDBC.findByPrimaryKey(bd.getBundle_id());
			
			bundles.add(b);
		}
		return bundles;
	}
}
