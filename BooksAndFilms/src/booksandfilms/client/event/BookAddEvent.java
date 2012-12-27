package booksandfilms.client.event;

import booksandfilms.client.entities.Author;

import com.google.gwt.event.shared.GwtEvent;

public class BookAddEvent extends GwtEvent<BookAddEventHandler> {
	public static Type<BookAddEventHandler> TYPE = new Type<BookAddEventHandler>();
	private final Author author;

	public BookAddEvent(Author author) {
		this.author = author;
	}

	public Author getAuthor() {
		return author;
	}

	@Override
	public Type<BookAddEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookAddEventHandler handler) {
		handler.onAddBook(this);
	}

}
