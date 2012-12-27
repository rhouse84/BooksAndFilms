package booksandfilms.client.event;

import booksandfilms.client.entities.Media;

import com.google.gwt.event.shared.GwtEvent;

public class GetQuoteMapEvent extends GwtEvent<GetQuoteMapEventHandler> {
	public static Type<GetQuoteMapEventHandler> TYPE = new Type<GetQuoteMapEventHandler>();
	private final Media media;

	public GetQuoteMapEvent(Media media) {
		this.media = media;
	}
	
	public Media getMedia() {
		return media;
	}

	@Override
	public Type<GetQuoteMapEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GetQuoteMapEventHandler handler) {
		handler.onGetQuoteMap(this);
	}

}
