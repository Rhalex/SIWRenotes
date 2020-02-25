package it.unical.ingsw.siw.renotes.persistance.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.Preview;
import it.unical.ingsw.siw.renotes.model.Review;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.AdDao;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;
import it.unical.ingsw.siw.renotes.persistance.dao.DataSource;


public class AdDaoJDBC implements AdDao {
	
	private DataSource dataSource;
	
	public AdDaoJDBC(DataSource ds) {
		dataSource = ds;
	}


	public void save(Ad ad) {
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			String insert = "insert into inserzione(titolo,materia,universita,corso_di_laurea,descrizione,prezzo,file,anteprima) values(?,?,?,?,?,?,?,?)";
			
			PreparedStatement stm = connection.prepareStatement(insert);
			//LE COLONNE CON IL serial NON NECESSITANO DI AGGIUNTA DEI VALORI TRAMITE QUERY.
			stm.setString(1, ad.getTitle());
			stm.setString(2, ad.getSubject());
			stm.setString(3, ad.getUniversity());
			stm.setString(4, ad.getDegreeCourse());
			stm.setString(5, ad.getDescription());
			stm.setDouble(6, ad.getPrice());
			stm.setString(7, ad.getFile());
			stm.setInt(8, ad.getPreview().getId());
			/*
			FileInputStream fis = null;
			try {
				if(ad.getFile() != null)
					fis = new FileInputStream( ad.getFile().getPath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			stm.setBinaryStream(7, fis);
			
			*/
			
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

	public Ad findByPrimaryKey(Integer id) {
		Ad ad = null;
		int previewId = 0;
		Connection connection=null;
		
		try {
			connection = dataSource.getConnection();
			
			PreparedStatement stm;
			String query = "select * from inserzione where inserzione_id=?";
			stm = connection.prepareStatement(query);
			stm.setInt(1, id);
			
			ResultSet result = stm.executeQuery();
			
			if(result.next())
			{
				ad = new Ad();
				ad.setId(result.getInt("inserzione_id"));
				ad.setTitle(result.getString("titolo"));
				ad.setSubject(result.getString("materia"));
				ad.setUniversity(result.getString("universita"));
				ad.setDegreeCourse(result.getString("corso_di_laurea"));
				/*
				InputStream is =  result.getBinaryStream("file");
				ObjectInputStream in = null;
				try {
					in = new ObjectInputStream(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File file = null;
				try {
					file = (File) in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				ad.setFile(result.getString("file"));
				ad.setPrice(result.getDouble("prezzo"));
				ad.setDescription(result.getString("descrizione"));
				ad.setConsultable(result.getBoolean("consultabile"));
				previewId = result.getInt("anteprima");	
				ad.setVersion(result.getDouble("versione"));
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
		
		PreviewDaoJDBC previewDaoJDBC = new PreviewDaoJDBC(dataSource);
		
		Preview preview = previewDaoJDBC.findByPrimaryKey(previewId);
		
		ad.setPreview(preview);
		
		return ad;
	}

	public void update(Ad ad) {
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			String update = "update inserzione SET titolo=?, materia=?, universita=?, corso_di_laurea=?, descrizione=?, prezzo=?, file=?, anteprima=?, consultabile=?, versione=? WHERE inserzione_id=?";
			
			PreparedStatement stm = connection.prepareStatement(update);
			
			stm.setString(1, ad.getTitle());
			stm.setString(2, ad.getSubject());
			stm.setString(3, ad.getUniversity());
			stm.setString(4, ad.getDegreeCourse());
			stm.setString(5, ad.getDescription());
			stm.setDouble(6, ad.getPrice());
			stm.setString(7, ad.getFile());
			stm.setInt(8, ad.getPreview().getId());
			stm.setBoolean(9, ad.isConsultable());
			stm.setDouble(10, ad.getVersion());
			stm.setInt(11, ad.getId());
		
			
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

	public void delete(Ad ad) {
		//È NECESSARIO AGGIUNGERE UNA booleana DISPONIBILE/NONDISPONIBILE.
		//UTILE QUANDO UN VENDITORE ELIMINA UNA SUA INSERZIONE, ALCUNE PERSONE L'HANNO ACQUISTATAT QUINDI NON SI PUÒ WLIMINARE DAL DB
		//MA ALLO STESSO TEMPO NON PUÒ PIÙ COMPARIRE SUL SITO QUINDI DIVENTA NONDISPONIBILE
		
	}

	public List<Review> findReview(Integer adId) {
		List<Review> reviews = new ArrayList<Review>();
		
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			
			PreparedStatement stm;
			String query = "select * from valutazione where inserzione=?";
			stm = connection.prepareStatement(query);
			stm.setInt(1, adId);
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Review review = new Review();
	
				review.setId(result.getInt("valutazione_id"));
				review.setQuality(result.getInt("qualita"));
				review.setReliability(result.getInt("attendibilita"));
				review.setCompleteness(result.getInt("completezza"));
				review.setComment(result.getString("commento"));
				
				Ad ad = new Ad();
				ad.setId(adId);
				
				review.setAd(ad);
				
				User user = new User();
				user.setUsername(result.getString("utente_username"));
				user.setMail(result.getString("utente_mail"));
				
				review.setUser(user);
				
				reviews.add(review);
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
		
		return reviews;
	}


	public User findUserCreator(Integer adId) {
		User user = null;
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String query = "select utente_username, utente_mail from relazione_utente_inserzione where inserzione=? and e_acquistata=false";
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setInt(1, adId);
			
			ResultSet result = stm.executeQuery();
			
			if(result.next())
			{
				user = new User();
				user.setUsername(result.getString("utente_username"));
				user.setMail(result.getString("utente_mail"));
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
		
		return user;
	}

	public List<Ad> findAll(User user) {
		List<Ad> ads = new ArrayList<Ad>();
		List<Integer> previewsId = new ArrayList<Integer>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String select = "select inserzione_id, titolo, materia, prezzo, file, universita, corso_di_laurea, anteprima, versione from inserzione where consultabile=true AND inserzione.inserzione_id" 
			+ " NOT IN( select inserzione from relazione_utente_inserzione where utente_username=?)";
			PreparedStatement stm = connection.prepareStatement(select);
			stm.setString(1, user.getUsername());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Ad ad = new Ad();
				ad.setId(result.getInt("inserzione_id"));
				ad.setTitle(result.getString("titolo"));
				ad.setSubject(result.getString("materia"));
				ad.setPrice(result.getDouble("prezzo"));
				ad.setFile(result.getString("file"));
				ad.setUniversity(result.getString("universita"));
				ad.setDegreeCourse(result.getString("corso_di_laurea"));
				previewsId.add(result.getInt("anteprima"));
				
				ad.setVersion(result.getDouble("versione"));
				ads.add(ad);
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
		
		for(int i=0; i<previewsId.size(); i++)
		{
			PreviewDaoJDBC previewDaoJDBC = new PreviewDaoJDBC(dataSource);
			Preview preview = previewDaoJDBC.findByPrimaryKey(previewsId.get(i));
			ads.get(i).setPreview(preview);
		}
		
		return ads;
	}
	
	public List<Ad> findAdConsultable()
	{
		List<Ad> ads = new ArrayList<Ad>();
		List<Integer> previewsId = new ArrayList<Integer>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String select = "select * from inserzione where consultable=true";
			PreparedStatement stm = connection.prepareStatement(select);
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Ad ad = new Ad();
				ad.setId(result.getInt("inserzione_id"));
				ad.setTitle(result.getString("titolo"));
				ad.setSubject(result.getString("materia"));
				ad.setUniversity(result.getString("universita"));
				ad.setDegreeCourse(result.getString("corso_di_laurea"));
				ad.setPrice(result.getDouble("prezzo"));
				ad.setFile(result.getString("file"));	
				ad.setDescription(result.getString("descrizione"));
				ad.setConsultable(result.getBoolean("consultabile"));
				previewsId.add(result.getInt("anteprima"));
				
				ad.setVersion(result.getDouble("versione"));
				ads.add(ad);
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
		
		for(int i=0; i<previewsId.size(); i++)
		{
			PreviewDaoJDBC previewDaoJDBC = new PreviewDaoJDBC(dataSource);
			Preview preview = previewDaoJDBC.findByPrimaryKey(previewsId.get(i));
			ads.get(i).setPreview(preview);
		}
		
		return ads;
	}
	
	public void updateConsultable(Integer adId) {
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			String update = "update inserzione SET consultabile=? WHERE inserzione_id=?";
			
			PreparedStatement stm = connection.prepareStatement(update);
			
			stm.setBoolean(1, false);
			stm.setInt(2, adId);
			
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
	
	public int lastSerialAdId() {
		Connection connection = null;
		int val = 0;
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement statement;
			String query = "select max(inserzione_id) from inserzione";
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


	public List<User> findBuyer(Ad ad) {
		List<User> users = new ArrayList<User>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			String select = "select utente_username, utente_mail from relazione_utente_inserzione where inserzione=? AND e_acquistata=true";
			
			PreparedStatement stm = connection.prepareStatement(select);
			stm.setInt(1, ad.getId());
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				User user = new User();
				user.setUsername(result.getString("utente_username"));
				user.setMail(result.getString("utente_mail"));
				
				users.add(user);
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return users;
	}


	public List<Ad> findAllNoSession() {
		List<Ad> ads = new ArrayList<Ad>();
		List<Integer> previewsId = new ArrayList<Integer>();
		Connection connection = null;
		
		try 
		{
			connection = dataSource.getConnection();
			
			String select = "select inserzione_id, titolo, materia, prezzo, file, universita, corso_di_laurea, anteprima, versione from inserzione where consultabile=true";
			PreparedStatement stm = connection.prepareStatement(select);
			
			ResultSet result = stm.executeQuery();
			
			while(result.next())
			{
				Ad ad = new Ad();
				ad.setId(result.getInt("inserzione_id"));
				ad.setTitle(result.getString("titolo"));
				ad.setSubject(result.getString("materia"));
				ad.setPrice(result.getDouble("prezzo"));
				ad.setFile(result.getString("file"));
				ad.setUniversity(result.getString("universita"));
				ad.setDegreeCourse(result.getString("corso_di_laurea"));
				previewsId.add(result.getInt("anteprima"));
				
				ad.setVersion(result.getDouble("versione"));
				ads.add(ad);
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
		
		for(int i=0; i<previewsId.size(); i++)
		{
			PreviewDaoJDBC previewDaoJDBC = new PreviewDaoJDBC(dataSource);
			Preview preview = previewDaoJDBC.findByPrimaryKey(previewsId.get(i));
			ads.get(i).setPreview(preview);
		}
		
		return ads;
	}


	public HashMap<Integer, Integer> findAdsMostReviewedNoSession() {
		
		HashMap<Integer, Integer> ads = new HashMap<Integer, Integer>();
		Connection connection= null;
		
		try 
		{
			connection = dataSource.getConnection();
		
			String query = "select valutazione.inserzione, count(valutazione.inserzione) as num"
					+ " from valutazione, inserzione"
					+ " where valutazione.inserzione = inserzione_id and consultabile = true"
					+ " group by valutazione.inserzione";
		
			PreparedStatement stm = connection.prepareStatement(query);
			
			ResultSet result = stm.executeQuery();
			
			int onlyFirstSix=0;
			while(result.next() && onlyFirstSix<6)
			{
				ads.put(result.getInt("inserzione"), result.getInt("num"));
				onlyFirstSix++;
				
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
		
		return ads;
	}

	public HashMap<Integer, Integer> findAdsMostReviewed(User user) {
	
		HashMap<Integer, Integer> ads = new HashMap<Integer, Integer>();
		Connection connection= null;
		
		try 
		{
			connection = dataSource.getConnection();
		
			String query = "select valutazione.inserzione, count(valutazione.inserzione) as num"
					+ " from valutazione, inserzione"
					+ " where valutazione.inserzione = inserzione_id and consultabile = true"
					+ " and inserzione_id"
					+ " NOT IN( select inserzione "
					+ " from relazione_utente_inserzione "
					+ " where utente_username=? and utente_mail = ?)"
					+ " group by valutazione.inserzione";
		
			PreparedStatement stm = connection.prepareStatement(query);
			
			stm.setString(1, user.getUsername());
			stm.setString(2, user.getMail());
			
			ResultSet result = stm.executeQuery();
			
			int onlyFirstSix=0;
			while(result.next() && onlyFirstSix<6)
			{
				ads.put(result.getInt("inserzione"), result.getInt("num"));
				onlyFirstSix++;
				
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
		
		return ads;
	}
}
