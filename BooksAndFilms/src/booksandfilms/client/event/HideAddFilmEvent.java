package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class HideAddFilmEvent extends GwtEvent<HideAddFilmEventHandler> {
	public static Type<HideAddFilmEventHandler> TYPE = new Type<HideAddFilmEventHandler>();
	
	public HideAddFilmEvent() {}

	@Override
	public Type<HideAddFilmEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HideAddFilmEventHandler handler) {
		handler.onHideAddFilm(this);
	}

}
