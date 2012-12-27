package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowTopicListEvent extends GwtEvent<ShowTopicListEventHandler> {
	public static Type<ShowTopicListEventHandler> TYPE = new Type<ShowTopicListEventHandler>();
	public ShowTopicListEvent() {}

	@Override
	public Type<ShowTopicListEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowTopicListEventHandler handler) {
		handler.onShowTopicList(this);
	}

}
