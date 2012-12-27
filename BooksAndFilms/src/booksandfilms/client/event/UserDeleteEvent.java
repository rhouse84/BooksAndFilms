package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UserDeleteEvent extends GwtEvent<UserDeleteEventHandler> {
	public static Type<UserDeleteEventHandler> TYPE = new Type<UserDeleteEventHandler>();
	
	@Override
	public Type<UserDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserDeleteEventHandler handler) {
		handler.onDeleteUser(this);	
	}

}
