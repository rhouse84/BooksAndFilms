package booksandfilms.client.event;

import booksandfilms.client.entities.Director;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DirectorDeleteEvent extends GwtEvent<DirectorDeleteEventHandler> implements EventHandler {
	public static Type<DirectorDeleteEventHandler> TYPE = new Type<DirectorDeleteEventHandler>();
	private final Director director;
	
	public DirectorDeleteEvent(Director director) {
		this.director = director;
	}
	
	public Director getDirector() {
		return director;
	}

	@Override
	public Type<DirectorDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DirectorDeleteEventHandler handler) {
		handler.onDeleteDirector(this);	
	}

}
