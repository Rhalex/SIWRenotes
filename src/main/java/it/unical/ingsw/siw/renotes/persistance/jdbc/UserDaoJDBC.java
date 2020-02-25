package it.unical.ingsw.siw.renotes.persistance.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unical.ingsw.siw.renotes.model.*;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.persistance.dao.DataSource;
import it.unical.ingsw.siw.renotes.persistance.dao.UserDao;



public class UserDaoJDBC implements UserDao{

	private DataSource dataSource;

	public UserDaoJDBC(DataSource ds) {
		dataSource = ds;
	}

	public void save(User user) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String insert = "insert into utente(username, mail, password, carrello, rate) values(?,?,?,?,?)";

			PreparedStatement stm = connection.prepareStatement(insert);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setString(3, user.getPassword());
			stm.setInt(4, user.getCart().getId());
			stm.setInt(5, user.getRate());
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

	public User findByPrimaryKey(String name, String email) {
		User user = null;
		int cartId = 0;
		Connection connection=null;

		try {
			connection = dataSource.getConnection();

			PreparedStatement stm;
			String query = "select * from utente where username=? AND mail=?";
			stm = connection.prepareStatement(query);
			stm.setString(1, name);
			stm.setString(2, email);


			ResultSet result = stm.executeQuery();

			if(result.next())
			{
				user = new User();
				user.setUsername(result.getString("username"));
				user.setMail(result.getString("mail"));
				user.setPassword(result.getString("password"));
				user.setRate(result.getInt("rate"));
				user.setImage(result.getString("immagineprofilo"));
				cartId = result.getInt("carrello");

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

		CartDaoJDBC cartDaoJDBC = new CartDaoJDBC(dataSource);
		Cart c = cartDaoJDBC.findByPrimaryKey(cartId);

		user.setCart(c);

		return user;

	}

	public void update(User user) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String update = "update utente SET password=?, carrello=?, rate=? WHERE username=? AND mail=?";

			PreparedStatement stm = connection.prepareStatement(update);

			
			stm.setString(1, user.getPassword());
			stm.setInt(2, user.getCart().getId());
			stm.setInt(3, user.getRate());
			stm.setString(4, user.getUsername());
			stm.setString(5, user.getMail());
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

	public void delete(User user) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String update = "DELETE FROM utente WHERE  username=? AND mail=?";

			PreparedStatement stm = connection.prepareStatement(update);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());

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

	public List<Ad> findBoughtAd(User user) {
		List<Ad> ad = new ArrayList<Ad>();

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			PreparedStatement stm;
			String query = "select inserzione from relazione_utente_inserzione where e_acquistata=true AND utente_username=?";
			stm = connection.prepareStatement(query);
			stm.setString(1, user.getUsername());

			ResultSet result = stm.executeQuery();

			while(result.next())
			{
				Ad a = new Ad();
				a.setId(result.getInt("inserzione"));

				ad.add(a);
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

		return ad;
	}

	public List<Ad> findManagedAd(User user) {
		List<Ad> ad = new ArrayList<Ad>();

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			PreparedStatement stm;
			String query = "select inserzione, titolo, prezzo from relazione_utente_inserzione, inserzione where inserzione.inserzione_id=relazione_utente_inserzione.inserzione AND inserzione.consultabile=true AND e_acquistata=false AND utente_username=?";
			stm = connection.prepareStatement(query);
			stm.setString(1, user.getUsername());

			ResultSet result = stm.executeQuery();

			while(result.next())
			{
				Ad a = new Ad();
				a.setId(result.getInt("inserzione"));
				a.setTitle(result.getString("titolo"));
				a.setPrice(result.getDouble("prezzo"));
				ad.add(a);
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
		
		return ad;
	}

	public List<PaymentMethod> findPaymentMethods(User user) {
		List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			PreparedStatement stm;
			String query = "select metodo_di_pagamento from lista_metodi_di_pagamento where utente_username=? AND utente_mail=?";
			stm = connection.prepareStatement(query);
			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());

			ResultSet result = stm.executeQuery();

			while(result.next())
			{
				PaymentMethod pm = new PaymentMethod();
				pm.setCardNumber(result.getString("metodo_di_pagamento"));

				paymentMethods.add(pm);
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

		return paymentMethods;
	}

	public void insertBoughtAd(User user, Integer id) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String insert = "insert into relazione_utente_inserzione(utente_username, utente_mail, inserzione, e_acquistata) values(?,?,?,true)";

			PreparedStatement stm = connection.prepareStatement(insert);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setInt(3, id);
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

	public void deleteBoughtAd(User user, Integer id) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String update = "DELETE FROM relazione_utente_inserzione WHERE utente_username=? AND utente_mail=? AND inserzione=? AND e_acquistata=true";

			PreparedStatement stm = connection.prepareStatement(update);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setInt(3, id);

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

	public void insertManagedAd(User user, Integer id) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String insert = "insert into relazione_utente_inserzione(utente_username, utente_mail, inserzione, e_acquistata) values(?,?,?,false)";

			PreparedStatement stm = connection.prepareStatement(insert);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setInt(3, id);
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

	public void deleteManagedAd(User user, Integer id) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String update = "DELETE FROM relazione_utente_inserzione WHERE  utente_username=? AND utente_mail=? AND inserzione=? AND e_acquistata=false";

			PreparedStatement stm = connection.prepareStatement(update);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setInt(3, id);

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

	public void insertPaymentMethods(User user, String cardNumber) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String insert = "insert into lista_metodi_di_pagamento(utente_username, utente_mail, metodo_di_pagamento) values(?,?,?)";

			PreparedStatement stm = connection.prepareStatement(insert);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setString(3, cardNumber);
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

	public void deletePaymentMethods(User user, String cardNumber) {
		Connection connection = null;

		try {
			connection = dataSource.getConnection();
			String update = "DELETE FROM lista_metodi_di_pagamento WHERE  utente_username=? AND utente_mail=? AND metodo_di_pagamento=?";

			PreparedStatement stm = connection.prepareStatement(update);

			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			stm.setString(3, cardNumber);

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
	
	public User findByUsername(String name) {
		User user = null;
		int cartId = 0;
		Connection connection=null;

		try {
			connection = dataSource.getConnection();

			PreparedStatement stm;
			String query = "select * from utente where username=?";
			stm = connection.prepareStatement(query);
			stm.setString(1, name);
			
			ResultSet result = stm.executeQuery();

			if(result.next())
			{
				user = new User();
				user.setUsername(result.getString("username"));
				user.setMail(result.getString("mail"));
				user.setPassword(result.getString("password"));
				user.setRate(result.getInt("rate"));
				user.setImage(result.getString("immagineprofilo"));
				cartId = result.getInt("carrello");

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

		CartDaoJDBC cartDaoJDBC = new CartDaoJDBC(dataSource);
		Cart c = cartDaoJDBC.findByPrimaryKey(cartId);

		user.setCart(c);

		return user;
	}

	public String getVerificationCode(User user) {
		String code = "";
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "select verificationCode from utente where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			
			cmd.setString(1, user.getMail());
			ResultSet res = cmd.executeQuery();
			
			while(res.next()) 
				code = res.getString("verificationcode");
			
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
		
		return code;
	}

	public boolean getTwoFactorAutenticationActivated(User user) {
		boolean isActivated = false;
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "select twofactorautentication from utente where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			cmd.setString(1, user.getMail());
			ResultSet res = cmd.executeQuery();
			
			while(res.next()) 
				isActivated = res.getBoolean("twofactorautentication");
			
		} 
		catch (SQLException e) 
		{
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
		
		return isActivated;
	}

	public void setVerificationCode(User user, String code) {
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "update utente set verificationcode = ? where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			cmd.setString(1, code);
			cmd.setString(2, user.getMail());
			cmd.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
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

	public void modifyUserPwd(User user, String password) {
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "update utente set password = ? where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			cmd.setString(1, password);
			cmd.setString(2, user.getMail());
			cmd.executeUpdate();

		} 
		catch (SQLException e) 
		{
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

	public void modifyImage(User user, String image) {
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "update utente set immagineprofilo = ? where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			cmd.setString(1, image);
			cmd.setString(2, user.getMail());
			cmd.executeUpdate();
			
		} 
		catch (SQLException e) 
		{
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

	public String getUserImage(User user) {
		String image = null;
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "select immagineprofilo from utente where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			cmd.setString(1, user.getMail());
			ResultSet res = cmd.executeQuery();
			
			while(res.next()) 
				image = res.getString("immagineprofilo");
		} 
		catch (SQLException e) 
		{
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
		
		return image;
	}
	
	public User findByMail(String mail) 
	{
		User user = null;
		int cartId = 0;
		Connection connection = null;
		try 
		{
			connection = dataSource.getConnection();
			String query = "select * from utente where mail = ?";
			PreparedStatement cmd = connection.prepareStatement(query);
			cmd.setString(1, mail);
			
			ResultSet res = cmd.executeQuery();
			
			while(res.next())
			{
				user = new User();
				user.setUsername(res.getString("username"));
				user.setMail(res.getString("mail"));
				user.setPassword(res.getString("password"));
				user.setImage(res.getString("immagineprofilo"));
				user.setRate(res.getInt("rate"));
				cartId = res.getInt("carrello");
			}
			connection.close();
		} 
		catch (SQLException e) 
		{
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
		
		CartDaoJDBC cartDaoJDBC = new CartDaoJDBC(dataSource);
		Cart c = cartDaoJDBC.findByPrimaryKey(cartId);
		
		if(c != null)
		{
			user.setCart(c);
		}
		
		return user;
	}
	//RESTITUISCE L'ID DELLE INSERZIONI PIÙ ACQUISTATE INSIEME A QUANTE VOLTE È STATA ACQUISTATA
	public HashMap<Integer, Integer> findPopularAds(User user) {
		
		HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			/*String query4 = "select relazione_utente_inserzione.inserzione, count(inserzione) as num"
					+ " from relazione_utente_inserzione"
					+ " where"
				//	+ " where utente_username!=? and utente_mail!=?"
					+ " e_acquistata=true and"
					+ " relazione_utente_inserzione.inserzione"
					+ " IN (select relazione_utente_inserzione.inserzione from inserzione where relazione_utente_inserzione.inserzione = inserzione_id"
					+ " and consultabile=true and e_acquistata=false"
					+ " and utente_username=? and utente_mail=?)"
					+ " group by relazione_utente_inserzione.inserzione";
			
			String query2 = "select relazione_utente_inserzione.inserzione"
					+ " from inserzione, relazione_utente_inserzione"
					+ " where relazione_utente_inserzione.inserzione = inserzione_id"
					+ " and consultabile=true and e_acquistata=false"
					+ " and utente_username=? and utente_mail=?";
			
			String query3 = "select relazione_utente_inserzione.inserzione, count(inserzione) as num"
					+ " from relazione_utente_inserzione"
					+ " where e_acquistata=true and utente_username!=? and utente_mail!=?"
					+ " group by relazione_utente_inserzione.inserzione";*/
			
			String query = "select relazione_utente_inserzione.inserzione, count(inserzione) as num"
					+ " from relazione_utente_inserzione"
					+ "	where relazione_utente_inserzione.inserzione IN"
					+ " (select relazione_utente_inserzione.inserzione"
					+ " from inserzione, relazione_utente_inserzione"
					+ " where relazione_utente_inserzione.inserzione = inserzione_id"
					+ " and consultabile=true and e_acquistata=false"
					+ " and utente_username=? and utente_mail=?)"
					+ "group by relazione_utente_inserzione.inserzione";
					
			PreparedStatement stm = connection.prepareStatement(query);
			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				data.put(result.getInt("inserzione"), result.getInt("num") -1);
			}
			
		} 
		catch (SQLException e) 
		{
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
		
		return data;
	}

	public List<Bundle> findMyBundle(User user) {
		List<Bundle> bundles = new ArrayList<Bundle>();
		List<Integer> bundlesId = new ArrayList<Integer>();
		
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String select = "select bundle_id from bundle where user_username=? and user_mail=?";
			
			PreparedStatement stm = connection.prepareStatement(select);
			
			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				bundlesId.add(result.getInt("bundle_id"));
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
		
		for(Integer bundleId: bundlesId)
		{
			Bundle bundle = DBManager.getInstance().getBundleDao().findByPrimaryKey(bundleId);
			bundles.add(bundle);
		}
		
		return bundles;
	}
}
