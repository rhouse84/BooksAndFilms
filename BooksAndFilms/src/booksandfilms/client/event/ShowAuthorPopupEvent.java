package booksandfilms.client.event;

import booksandfilms.client.entities.Author;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.GwtEvent;

public class ShowAuthorPopupEvent extends GwtEvent<ShowAuthorPopupEventHandler> {
	public static Type<ShowAuthorPopupEventHandler> TYPE = new Type<ShowAuthorPopupEventHandler>();
	private final Author author;
	private final ClickPoint point;
	
	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowAuthorPopupEvent(Author author, ClickPoint point) {
		this.author = author;
		this.point = point;
	}

	public Author getAuthor() {
		return author;
	}

	@Override
	public Type<ShowAuthorPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowAuthorPopupEventHandler handler) {
		handler.onShowAuthorPopup(this);
	}

}
