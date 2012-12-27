package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BookEditCancelledEvent extends	GwtEvent<BookEditCancelledEventHandler> {
	public static Type<BookEditCancelledEventHandler> TYPE = new Type<BookEditCancelledEventHandler>();

	@Override
	public Type<BookEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookEditCancelledEventHandler handler) {
		handler.onEditCancelBook(this);
	}

}
