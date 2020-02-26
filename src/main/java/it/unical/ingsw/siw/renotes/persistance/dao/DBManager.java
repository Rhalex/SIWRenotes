package it.unical.ingsw.siw.renotes.persistance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Bundle;
import it.unical.ingsw.siw.renotes.model.Preview;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.jdbc.AdDaoJDBC;
import it.unical.ingsw.siw.renotes.persistance.jdbc.BundleDaoJDBC;
import it.unical.ingsw.siw.renotes.persistance.jdbc.CartDaoJDBC;
import it.unical.ingsw.siw.renotes.persistance.jdbc.PaymentDaoJDBC;
import it.unical.ingsw.siw.renotes.persistance.jdbc.PreviewDaoJDBC;
import it.unical.ingsw.siw.renotes.persistance.jdbc.ReviewDaoJDBC;
import it.unical.ingsw.siw.renotes.persistance.jdbc.UserDaoJDBC;


public class DBManager {
	
	
	private static  DataSource dataSource;

	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			//questi vanno messi in file di configurazione!!!	
			//dataSource=new DataSource("jdbc:postgresql://manny.db.elephantsql.com:5432/nzxxsfok","nzxxsfok","5JTu5JBBv9l17WPT1rFhHHpp2OAZ4iuY");
			dataSource=new DataSource("jdbc:postgresql://localhost:5432/ReNotesLocal","postgres","postgres");

		} 
		catch (Exception e) {
			System.err.println("PostgresDAOFactory.class: failed to load MySQL JDBC driver\n"+e);
			e.printStackTrace();
		}
	}
	
	public static DBManager instance = null;
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager()	
	{}
	
	public AdDao getAdDao()
	{
		return new AdDaoJDBC(dataSource);
	}
	
	public UserDao getUserDao()
	{
		return new UserDaoJDBC(dataSource);
	}
	
	public void insertAd(Ad ad)
	{
		this.getAdDao().save(ad);
	}
	
	public PreviewDao getPreviewDao()
	{
		return new PreviewDaoJDBC(dataSource);
	}
	
	public void insertPreview(Preview preview)
	{
		this.getPreviewDao().save(preview);
	}
	
	public ReviewDaoJDBC getReviewDao()
	{
		return new ReviewDaoJDBC(dataSource);
	}
	
	public CartDao getCartDao()
	{
		return new CartDaoJDBC(dataSource);
	}
	
	
	public PaymentMethodDao getPaymentMethodDao()
	{
		return new PaymentDaoJDBC(dataSource);
	}
	
	public BundleDao getBundleDao()
	{
		return new BundleDaoJDBC(dataSource);
	}
	
	public void resetSerialPreview() {
		Connection connection = null;
		
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement statement;
			String query = "select setval('\"anteprima_anteprimaId_seq\"',2,false)";
			statement = connection.prepareStatement(query);

			statement.executeQuery();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}
	
	public void resetSerialAd() {
		Connection connection = null;
		
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement statement;
			String query = "select setval('\"Inserzione_inserioneId_seq\"',1,false)";
			statement = connection.prepareStatement(query);

			statement.executeQuery();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}
	
	public void resetSerialCart() {
		Connection connection = null;
		
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement statement;
			String query = "select setval('\"carrello_carrelloId_seq\"',1,false)";
			statement = connection.prepareStatement(query);

			statement.executeQuery();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}
	
	public void resetSerialReview() {
		Connection connection = null;
		
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement statement;
			String query = "select setval('\"valutazione_valutazioneId_seq\"',1,false)";
			statement = connection.prepareStatement(query);

			statement.executeQuery();
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}
}
