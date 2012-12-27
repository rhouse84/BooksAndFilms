package booksandfilms.client.event;

import booksandfilms.client.entities.Author;

import com.google.gwt.event.shared.GwtEvent;

public class ShowAddBookEvent extends GwtEvent<ShowAddBookEventHandler> {
	public static Type<ShowAddBookEventHandler> TYPE = new Type<ShowAddBookEventHandler>();
	private final Author author;
	
	public ShowAddBookEvent(Author author) {
		this.author = author;
	}

	public Author getAuthor() {
		return author;
	}

	@Override
	public Type<ShowAddBookEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowAddBookEventHandler handler) {
		handler.onShowAddBook(this);
	}

}
