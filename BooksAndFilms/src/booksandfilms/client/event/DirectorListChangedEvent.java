package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Director;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DirectorListChangedEvent extends GwtEvent<DirectorListChangedEventHandler> implements EventHandler {
	public static Type<DirectorListChangedEventHandler> TYPE = new Type<DirectorListChangedEventHandler>();
	private final List<Director> directors;
	private final List<Director> someDirectors;

	public DirectorListChangedEvent (List<Director> list, List<Director> all) {
		this.directors = all;
		this.someDirectors = list;
	}
	
	public List<Director> getAllDirectors(){ return directors; }
	public List<Director> getSelectDirectors(){ return someDirectors; }
	
	@Override
	public Type<DirectorListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DirectorListChangedEventHandler handler) {
		handler.onChangeDirectorList(this);
	}

}
