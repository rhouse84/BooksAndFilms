package booksandfilms.server;

import javax.servlet.http.HttpSession;

import booksandfilms.client.LoginService;
import booksandfilms.client.entities.UserAccount;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import booksandfilms.shared.exception.NotLoggedInException;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	public UserAccount getLoggedInUser() {
	    HttpSession session = getThreadLocalRequest().getSession();

	    DAO dao = new DAO();
	    if (session == null) {
	    	return null; //User not logged in
	    }
	    
	    String userId = (String) session.getAttribute("userId");
	    if (userId == null) {
	    	return null; //User not logged in
	    }
	    
	    //The presence of an altId means that we have an NON application user
	    //who will be browsing the owner's data. This id was set in the LoginHelper.
	    String altId = (String) session.getAttribute("altId");
	    if (altId == null) {
	    	
	    	//No altId, so therefore just get normal user.
		    
	    	Long id = Long.parseLong(userId.trim());

		    try {
		    	UserAccount currentUser = dao.ofy().get(UserAccount.class, id);
		    	return currentUser;
		    } finally {}
	    	
	    } else {
	    	
	    	//Get the alt user and the owner user. Show alt name with owner id.
		    
	    	Long laltId = Long.parseLong(altId.trim());
		    Long id = Long.parseLong(userId.trim());
		    try {
		    	UserAccount altUser = dao.ofy().get(UserAccount.class, laltId);
		    	UserAccount currentUser = dao.ofy().get(UserAccount.class, id);
		    	currentUser.setName(altUser.getName());
		    	currentUser.setAdminUser(false);
		    	currentUser.setUltimateUser(false);
		    	return currentUser;
		    } finally {}
	    }
	}
	
	@Override
	public void logout() throws NotLoggedInException {
		this.getThreadLocalRequest().getSession().invalidate();
		throw new NotLoggedInException("Logged out");
	}

}
