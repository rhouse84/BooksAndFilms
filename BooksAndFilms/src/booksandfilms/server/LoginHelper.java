package booksandfilms.server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import booksandfilms.client.entities.UserAccount;
import booksandfilms.server.utils.ServletHelper;
import booksandfilms.server.utils.ServletUtils;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;

public class LoginHelper extends RemoteServiceServlet {

	private static final long serialVersionUID = 6911161842353195517L;

	private static Logger log = Logger.getLogger(LoginHelper.class.getName());

	static public String getApplitionURL(HttpServletRequest request) {

		if (ServletHelper.isDevelopment(request)) {
			return "http://127.0.0.1:8888/BooksAndFilms.html?gwt.codesvr=127.0.0.1:9997";
		} else {
			return ServletUtils.getBaseUrl(request);
		}
	}
	
	static public UserAccount getLoggedInUser(HttpSession session) {
		String userId = (String) session.getAttribute("userId");

		DAO dao = new DAO();
    	
    	UserAccount currentUser = dao.ofy().get(UserAccount.class, Long.parseLong(userId));
		return currentUser;
	}
	
	public UserAccount loginStarts(HttpSession session, UserAccount user) {
		
	    UserService userService = UserServiceFactory.getUserService();
	    //This gets the current logged-in Google user.
	    User gUser = userService.getCurrentUser();
		UserAccount currentUser = null;
		String holdName = null;
		try {
			currentUser = getCurrentUser(user);
		} catch (NotFoundException e1) {
			log.log(Level.SEVERE, "Could not match Google user with app user");
		}

		DAO dao = new DAO();
    	
	    if (currentUser == null) {
	    	//Since we couldn't find an app user of the same id, put a new record.
	    	currentUser = user;
			currentUser.setName(gUser.getNickname());
		    currentUser.setEmailAddress(gUser.getEmail());
			currentUser.setAdminUser(false);
			currentUser.setUltimateUser(false);
	    } 
		
		Date date = new Date();
		currentUser.setLastLoginOn(date);
		//TODO: This IF should be removed after I have logged into production. Then it can't be changed.
		if (currentUser.getEmailAddress().equalsIgnoreCase("rhouse84@gmail.com")) {
			currentUser.setUltimateUser(true);
			currentUser.setAdminUser(true);
		}

		dao.ofy().put(currentUser);
	    
	    if (currentUser.getAdminUser()) {
	    
	    	//Since this is an application user, don't need an altId, if one exists.
	        session.removeAttribute("altId");

	    } else {

	    	//User is NOT an application user, so give them rhouse84@gmail.com
	    	//without update privledges so they can see my data. Yes, I know, it's what
			//they've always wanted.
			
			holdName = currentUser.getName();
	        session.setAttribute("altId", String.valueOf(currentUser.getId()));
			currentUser = null;
			currentUser = getDefaultUser(holdName);
	    }

        session.setAttribute("userId", String.valueOf(currentUser.getId()));
        session.setAttribute("loggedin", true);
		
		return currentUser;
	    
	}
	
	private UserAccount getCurrentUser(UserAccount user) throws NotFoundException {
		DAO dao = new DAO();
    	
    	UserAccount currentUser = dao.ofy().query(UserAccount.class).filter("uniqueId", user.getUniqueId()).get();
		return currentUser;
	}

	private UserAccount getDefaultUser(String holdName) throws NotFoundException {
		DAO dao = new DAO();
    	
    	UserAccount currentUser = dao.ofy().query(UserAccount.class).filter("emailAddress", "rhouse84@gmail.com").get();
    	currentUser.setName(holdName);
		return currentUser;
	}

}
