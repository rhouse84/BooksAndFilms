package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AuthorEditEvent extends GwtEvent<AuthorEditEventHandler> {
	public static Type<AuthorEditEventHandler> TYPE = new Type<AuthorEditEventHandler>();
	private final Long id;
	
	public AuthorEditEvent(Long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public Type<AuthorEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AuthorEditEventHandler handler) {
		handler.onEditAuthor(this);
	}

}
