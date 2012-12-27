package booksandfilms.client.event;

import booksandfilms.client.entities.Book;

import com.google.gwt.event.shared.GwtEvent;

public class BookDeleteEvent extends GwtEvent<BookDeleteEventHandler> {
	public static Type<BookDeleteEventHandler> TYPE = new Type<BookDeleteEventHandler>();
	private final Book book;

	public BookDeleteEvent(Book book) {
		this.book = book;
	}
	
	public Book getBook() {
		return book;
	}

	@Override
	public Type<BookDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookDeleteEventHandler handler) {
		handler.onDeleteBook(this);	
	}

}
