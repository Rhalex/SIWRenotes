package it.unical.ingsw.siw.renotes.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import it.unical.ingsw.siw.renotes.model.Ad;
import it.unical.ingsw.siw.renotes.model.User;
import it.unical.ingsw.siw.renotes.persistance.dao.DBManager;

public class EmailManager {
	
		private static final String from = "noreplyrenotes12@gmail.com";
		private static final String password = "33851242Re";
		
		protected static Session getSession() 
		{
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			
			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(from, password);
				}
			});
			
			return session;
		}
		
		public static void sendReviewMail(User user, Ad ad)
		{   
			String link = "http://localhost:8080/it.unical.ingsw.siw.renotes/reviewAdd" + "?adIdLink=" + ad.getId() + "&userLink=" + user.getUsername();
			
			String text = "Ciao " + user.getUsername() + ",\n" +
					"In seguito all'acquisto dell'inserzione:\n"+
					 ad.getTitle() + "\n" +
					"Puoi lasciare una valutazione al seguente link\n" +
					 link + "\n" + "Grazie per il tempo che dedicherai\n";
			
			try 
			{
				//Transport.send(prepareMessage(getSession(), user.getMail(), text)); //LA REALE
				Transport.send(prepareMessage(getSession(), "alessandrorocca96@gmail.com", text, "Recensisci il tuo acquisto")); //DI PROVA
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
		}
		
		public static void sendVendorMail(Ad ad)
		{   
			User user = DBManager.getInstance().getAdDao().findUserCreator(ad.getId());
			
			String text = "Ciao " + user.getUsername() + ",\n" +
					"ReNotes vuole informarti dell'avvenuto acquisto delle seguenti inserzioni:\n"+
					 ad.getTitle() + "\n" +
					 "Continua a pubblicare!\n";
			
			try 
			{
				//Transport.send(prepareMessage(getSession(), user.getMail(), text, "Notifica acquisto")); //LA REALE
				Transport.send(prepareMessage(getSession(), "alessandrorocca96@gmail.com", text, "Notifica acquisto")); //DI PROVA
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
		}
		
		public static void sendVendorReviewMail(Ad ad)
		{   
			User user = DBManager.getInstance().getAdDao().findUserCreator(ad.getId());
			
			String text = "Ciao " + user.getUsername() + ",\n" +
					"ReNotes vuole informarti che le seguenti inserzioni sono state recensite:\n"+
					 ad.getTitle() + "\n" +
					 "Continua a pubblicare!\n";
			
			try 
			{
				//Transport.send(prepareMessage(getSession(), user.getMail(), text, "Avvenuta recensione"));
				Transport.send(prepareMessage(getSession(), "alessandrorocca96@gmail.com", text, "Avvenuta recensione")); //DI PROVA
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
		}
		
		public static void sendBuyerNotifyMail(User user, Ad ad)
		{   
			
			String text = "Ciao " + user.getUsername() + ",\n" +
					"ReNotes vuole informarti che la seguente inserzione è stata modificata dal venditore:\n"+
					 ad.getTitle() + "\n" +
					 "Corri a visionarla!\n";
			
			try 
			{
				//Transport.send(prepareMessage(getSession(), user.getMail(), text,"Avvenuta modifica inserzione acquistata"));
				Transport.send(prepareMessage(getSession(), "alessandrorocca96@gmail.com", text, "Avvenuta modifica inserzione acquistata")); //DI PROVA

			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
		}
		
		public static void passwordModified(User user)
		{
			String text = "Ciao " + user.getUsername() + ",\n" +
						"la tua password è stata modificata";
			
			try 
			{
				Transport.send(prepareMessage(getSession(), user.getMail(), text, "Password modificata"));
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
		}
		
		public static int sendTwoFactorAutenticationCode(User user)
		{
			double code2 = Math.random() * ((9999 - 1000) + 1);
			int code = (int) code2;
			
			String text = "Il tuo codice di verifica è: " + code + ".\n" +
							"Inseriscilo nel form per completare il login.\n"
							+ "Saluti dallo staff di Renotes";
			
			try 
			{
				Transport.send(prepareMessage(getSession(), user.getMail(), text, "Codice di verifica"));
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
			return code;
		}
		
		public static void registerValidation(User user)
		{   
			String text = "Ciao " + user.getUsername() + ",\n" +
					"benvenuto su Renotes!!\n"+
					"La tua registrazione è stata completata, buon divertimento!";
			
			try 
			{
				Transport.send(prepareMessage(getSession(), user.getMail(), text,"Benvenuto su Renotes"));
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
		}
		
		protected static Message prepareMessage(Session session, String mailTo, String text, String subject)
		{
			MimeMessage message = new MimeMessage(session);
			try 
			{
				message.setFrom(new InternetAddress(from));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
				message.setSubject(subject);
				message.setText(text);
			} 
			catch (AddressException e) 
			{
				e.printStackTrace();
			} 
			catch (MessagingException e) 
			{
				e.printStackTrace();
			}
			
			return message;
		}
}
