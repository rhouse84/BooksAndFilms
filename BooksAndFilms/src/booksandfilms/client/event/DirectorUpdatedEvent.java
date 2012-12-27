package booksandfilms.client.event;

import booksandfilms.client.entities.Director;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DirectorUpdatedEvent extends GwtEvent<DirectorUpdatedEventHandler>	implements EventHandler {
	public static Type<DirectorUpdatedEventHandler> TYPE = new Type<DirectorUpdatedEventHandler>();
	private final Director director;
	
	public DirectorUpdatedEvent(Director director) {
		this.director = director;
	}

	public Director getDirector() {
		return director;
	}
	
	@Override
	public Type<DirectorUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DirectorUpdatedEventHandler handler) {
		handler.onUpdateDirector(this);
	}

}
