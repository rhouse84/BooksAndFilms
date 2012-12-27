package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BookEditEvent extends GwtEvent<BookEditEventHandler> {
	public static Type<BookEditEventHandler> TYPE = new Type<BookEditEventHandler>();
	private final Long id;

	public BookEditEvent(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	@Override
	public Type<BookEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookEditEventHandler handler) {
		handler.onEditBook(this);
	}

}
