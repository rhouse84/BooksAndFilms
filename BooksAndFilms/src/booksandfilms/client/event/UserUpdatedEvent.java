package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UserUpdatedEvent extends GwtEvent<UserUpdatedEventHandler> {
	public static Type<UserUpdatedEventHandler> TYPE = new Type<UserUpdatedEventHandler>();

	@Override
	public Type<UserUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserUpdatedEventHandler handler) {
		handler.onUpdateUser(this);
	}

}
