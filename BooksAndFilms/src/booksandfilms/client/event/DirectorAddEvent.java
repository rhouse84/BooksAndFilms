package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DirectorAddEvent extends GwtEvent<DirectorAddEventHandler>	implements EventHandler {
	public static Type<DirectorAddEventHandler> TYPE = new Type<DirectorAddEventHandler>();

	@Override
	public Type<DirectorAddEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DirectorAddEventHandler handler) {
		handler.onAddDirector(this);
	}

}
