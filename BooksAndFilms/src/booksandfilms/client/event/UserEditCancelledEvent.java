package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UserEditCancelledEvent extends GwtEvent<UserEditCancelledEventHandler> {
	public static Type<UserEditCancelledEventHandler> TYPE = new Type<UserEditCancelledEventHandler>();

	@Override
	public Type<UserEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserEditCancelledEventHandler handler) {
		handler.onEditCancelUser(this);
	}

}
