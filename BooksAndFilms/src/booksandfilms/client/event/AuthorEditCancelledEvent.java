package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuthorEditCancelledEvent extends GwtEvent<AuthorEditCancelledEventHandler> {
	public static Type<AuthorEditCancelledEventHandler> TYPE = new Type<AuthorEditCancelledEventHandler>();

	@Override
	public Type<AuthorEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthorEditCancelledEventHandler handler) {
		handler.onEditCancelAuthor(this);
	}

}
