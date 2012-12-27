package booksandfilms.client.event;

import booksandfilms.client.entities.Author;

import com.google.gwt.event.shared.GwtEvent;

public class AuthorUpdatedEvent extends GwtEvent<AuthorUpdatedEventHandler> {
	public static Type<AuthorUpdatedEventHandler> TYPE = new Type<AuthorUpdatedEventHandler>();
	private final Author author;
	
	public AuthorUpdatedEvent(Author author) {
		this.author = author;
	}
	
	public Author getAuthor() {
		return author;
	}

	@Override
	public Type<AuthorUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthorUpdatedEventHandler handler) {
		handler.onUpdateAuthor(this);
	}

}
