package booksandfilms.client.event;

import booksandfilms.client.entities.Book;

import com.google.gwt.event.shared.GwtEvent;

public class BookUpdatedEvent extends GwtEvent<BookUpdatedEventHandler> {
	public static Type<BookUpdatedEventHandler> TYPE = new Type<BookUpdatedEventHandler>();
	private final Book book;
	
	public BookUpdatedEvent(Book book) {
		this.book = book;
	}
	
	public Book getBook() {
		return book;
	}

	@Override
	public Type<BookUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookUpdatedEventHandler handler) {
		handler.onUpdateBook(this);
	}

}
