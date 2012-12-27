package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class HideAddBookEvent extends GwtEvent<HideAddBookEventHandler> {
	public static Type<HideAddBookEventHandler> TYPE = new Type<HideAddBookEventHandler>();
	
	public HideAddBookEvent() {}

	@Override
	public Type<HideAddBookEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HideAddBookEventHandler handler) {
		handler.onHideAddBook(this);
	}

}
