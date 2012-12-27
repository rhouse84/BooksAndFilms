package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DirectorEditEvent extends GwtEvent<DirectorEditEventHandler> implements EventHandler {
	public static Type<DirectorEditEventHandler> TYPE = new Type<DirectorEditEventHandler>();
	private final Long id;

	public DirectorEditEvent(Long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public Type<DirectorEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DirectorEditEventHandler handler) {
		handler.onEditDirector(this);
	}

}
