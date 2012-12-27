package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UserAddEvent extends GwtEvent<UserAddEventHandler> {
	public static Type<UserAddEventHandler> TYPE = new Type<UserAddEventHandler>();

	@Override
	public Type<UserAddEventHandler> getAssociatedType() {
		return TYPE;
	}

	protected void dispatch(UserAddEventHandler handler) {
		handler.onAddUser(this);
	}

}
