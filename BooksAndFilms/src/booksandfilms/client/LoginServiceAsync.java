package booksandfilms.client;

import booksandfilms.client.entities.UserAccount;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	void getLoggedInUser(AsyncCallback<UserAccount> callBack);
	void logout(AsyncCallback<Void> callBack);
}
