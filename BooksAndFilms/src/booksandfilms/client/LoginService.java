package booksandfilms.client;

import booksandfilms.client.entities.UserAccount;
import booksandfilms.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {
	UserAccount getLoggedInUser();
	void logout() throws NotLoggedInException;
}
