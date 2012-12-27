package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SetScrollHeightEvent extends GwtEvent<SetScrollHeightEventHandler> {
	public static Type<SetScrollHeightEventHandler> TYPE = new Type<SetScrollHeightEventHandler>();
	private final int scrollHeight;
	
	public SetScrollHeightEvent(int scrollHeight) {
		this.scrollHeight = scrollHeight;
	}
	
	public int getScrollHeight() {
		return scrollHeight;
	}
	
	@Override
	public Type<SetScrollHeightEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SetScrollHeightEventHandler handler) {
		handler.onSetHeight(this);
	}

}
