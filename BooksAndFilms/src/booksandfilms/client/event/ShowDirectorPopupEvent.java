package booksandfilms.client.event;

import booksandfilms.client.entities.Director;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ShowDirectorPopupEvent extends	GwtEvent<ShowDirectorPopupEventHandler> implements EventHandler {
	public static Type<ShowDirectorPopupEventHandler> TYPE = new Type<ShowDirectorPopupEventHandler>();
	private final Director director;
	private final ClickPoint point;

	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowDirectorPopupEvent(Director director, ClickPoint point) {
		this.director = director;
		this.point = point;
	}

	public Director getDirector() {
		return director;
	}

	@Override
	public Type<ShowDirectorPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowDirectorPopupEventHandler handler) {
		handler.onShowDirectorPopup(this);
	}

}
