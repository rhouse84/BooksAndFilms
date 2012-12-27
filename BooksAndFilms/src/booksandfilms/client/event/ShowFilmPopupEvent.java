package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Director;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ShowFilmPopupEvent extends GwtEvent<ShowFilmPopupEventHandler>	implements EventHandler {
	public static Type<ShowFilmPopupEventHandler> TYPE = new Type<ShowFilmPopupEventHandler>();
	private final Film film;
	private final ClickPoint point;
	private List<Director> allDirectors;
	private List<Director> someDirectors;
	
	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowFilmPopupEvent(Film film, ClickPoint point, List<Director> allDirectors, List<Director> someDirectors) {
		this.film = film;
		this.point = point;
		this.allDirectors = allDirectors;
		this.someDirectors = someDirectors;
	}

	public Film getFilm() {
		return film;
	}
	
	public List<Director> getAllDirectors() {
		return allDirectors;
	}

	public List<Director> getSomeDirectors() {
		return someDirectors;
	}

	@Override
	public Type<ShowFilmPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowFilmPopupEventHandler handler) {
		handler.onShowFilmPopup(this);
	}

}
