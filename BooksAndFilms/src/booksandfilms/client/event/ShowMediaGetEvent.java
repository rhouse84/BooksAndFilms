package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMediaGetEvent extends GwtEvent<ShowMediaGetEventHandler> {
	public static Type<ShowMediaGetEventHandler> TYPE = new Type<ShowMediaGetEventHandler>();
	private final int[] topLeft;
	private final String mediaName;
	
	public int[] getTopLeft() {
		return topLeft;
	}
	
	public String getMediaName() {
		return mediaName;
	}
	
	public ShowMediaGetEvent(int[] topLeft, String mediaName) {
		this.topLeft = topLeft;
		this.mediaName = mediaName;
	}

	@Override
	public Type<ShowMediaGetEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowMediaGetEventHandler handler) {
		handler.onShowMediaGet(this);
	}

}
