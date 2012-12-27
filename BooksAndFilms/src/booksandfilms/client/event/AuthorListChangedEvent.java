package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Author;

import com.google.gwt.event.shared.GwtEvent;

public class AuthorListChangedEvent extends	GwtEvent<AuthorListChangedEventHandler> {
	public static Type<AuthorListChangedEventHandler> TYPE = new Type<AuthorListChangedEventHandler>();
	private final List<Author> authors;
	private final List<Author> someAuthors;

	public AuthorListChangedEvent (List<Author> list, List<Author> all) {
		this.someAuthors = list;
		this.authors = all;
	}
	
	public List<Author> getAllAuthors(){ return authors; }
	public List<Author> getSelectAuthors() { return someAuthors; }
	
	@Override
	public Type<AuthorListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthorListChangedEventHandler handler) {
		handler.onChangeAuthorList(this);
	}

}
