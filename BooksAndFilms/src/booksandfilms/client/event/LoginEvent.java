package booksandfilms.client.event;

import booksandfilms.client.entities.UserAccount;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
	private final UserAccount user;
	
	public LoginEvent(UserAccount user) {
		this.user = user;
	}
	
	public UserAccount getUser() {
		return user;
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}

}
