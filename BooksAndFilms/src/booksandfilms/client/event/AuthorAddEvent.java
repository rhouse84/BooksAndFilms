package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuthorAddEvent extends GwtEvent<AuthorAddEventHandler> {
	public static Type<AuthorAddEventHandler> TYPE = new Type<AuthorAddEventHandler>();

	@Override
	public Type<AuthorAddEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthorAddEventHandler handler) {
		handler.onAddAuthor(this);
	}

}
