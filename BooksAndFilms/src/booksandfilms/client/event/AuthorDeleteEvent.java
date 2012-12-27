package booksandfilms.client.event;

import booksandfilms.client.entities.Author;

import com.google.gwt.event.shared.GwtEvent;

public class AuthorDeleteEvent extends GwtEvent<AuthorDeleteEventHandler> {
	public static Type<AuthorDeleteEventHandler> TYPE = new Type<AuthorDeleteEventHandler>();
	private final Author author;
	
	public AuthorDeleteEvent(Author author) {
		this.author = author;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	@Override
	public Type<AuthorDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthorDeleteEventHandler handler) {
		handler.onDeleteAuthor(this);	
	}

}
