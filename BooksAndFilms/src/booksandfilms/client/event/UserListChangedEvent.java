package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.UserAccount;

import com.google.gwt.event.shared.GwtEvent;

public class UserListChangedEvent extends	GwtEvent<UserListChangedEventHandler> {
	public static Type<UserListChangedEventHandler> TYPE = new Type<UserListChangedEventHandler>();
	private final List<UserAccount> users;
	private final List<UserAccount> someUsers;

	public UserListChangedEvent (List<UserAccount> list, List<UserAccount> all) {
		this.someUsers = list;
		this.users = all;
	}
	
	public List<UserAccount> getAllUsers(){ return users; }
	public List<UserAccount> getSelectUsers() { return someUsers; }
	
	@Override
	public Type<UserListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserListChangedEventHandler handler) {
		handler.onChangeUserList(this);
	}

}
