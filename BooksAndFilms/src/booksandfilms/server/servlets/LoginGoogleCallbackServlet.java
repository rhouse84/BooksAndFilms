package booksandfilms.server.servlets;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import booksandfilms.client.entities.UserAccount;
import booksandfilms.server.LoginHelper;
import booksandfilms.server.utils.AuthenticationProvider;

@SuppressWarnings("serial")
public class LoginGoogleCallbackServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(LoginGoogleCallbackServlet.class.getName());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Principal googleUser = request.getUserPrincipal();
		if (googleUser != null) {
			// update or create user
			UserAccount u = new UserAccount(googleUser.getName(),  AuthenticationProvider.GOOGLE);
			u.setName(googleUser.getName());
			UserAccount currentUser = new LoginHelper().loginStarts(request.getSession(), u);
		  
			log.info("User id:" + currentUser.getId().toString() + " " + request.getUserPrincipal().getName());
		}
		response.sendRedirect(LoginHelper.getApplitionURL(request));
	}
}
