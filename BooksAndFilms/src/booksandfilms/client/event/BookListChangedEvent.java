package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Book;

import com.google.gwt.event.shared.GwtEvent;

public class BookListChangedEvent extends GwtEvent<BookListChangedEventHandler> {
	public static Type<BookListChangedEventHandler> TYPE = new Type<BookListChangedEventHandler>();
	private final List<Book> books;

	public BookListChangedEvent (List<Book> all) {
		this.books = all;
	}
	
	public List<Book> getAllBooks(){ return books; }
	
	@Override
	public Type<BookListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BookListChangedEventHandler handler) {
		handler.onChangeBookList(this);
	}

}
