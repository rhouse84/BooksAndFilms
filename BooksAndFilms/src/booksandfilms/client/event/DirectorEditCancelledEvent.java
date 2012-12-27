package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DirectorEditCancelledEvent extends	GwtEvent<DirectorEditCancelledEventHandler> implements EventHandler {
	public static Type<DirectorEditCancelledEventHandler> TYPE = new Type<DirectorEditCancelledEventHandler>();

	@Override
	public Type<DirectorEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DirectorEditCancelledEventHandler handler) {
		handler.onEditCancelDirector(this);
	}

}
