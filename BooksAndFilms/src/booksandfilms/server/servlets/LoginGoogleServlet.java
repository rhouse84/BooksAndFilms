package booksandfilms.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import booksandfilms.server.utils.AuthenticationProvider;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginGoogleServlet extends LoginSuperServlet {

	private static final long serialVersionUID = 5694293814910028615L;
	private static Logger log = Logger.getLogger(LoginGoogleServlet.class.getName());
		  
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String callbackURL = buildCallBackURL(request, AuthenticationProvider.GOOGLE);
		UserService userService = UserServiceFactory.getUserService();
		String googleLoginUrl = userService.createLoginURL(callbackURL);
		log.info("Going to Google login URL: " + googleLoginUrl);
		response.sendRedirect(googleLoginUrl);
	}
}
