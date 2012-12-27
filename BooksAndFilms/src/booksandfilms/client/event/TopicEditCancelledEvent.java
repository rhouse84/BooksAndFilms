package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class TopicEditCancelledEvent extends GwtEvent<TopicEditCancelledEventHandler> {
	public static Type<TopicEditCancelledEventHandler> TYPE = new Type<TopicEditCancelledEventHandler>();

	@Override
	public Type<TopicEditCancelledEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopicEditCancelledEventHandler handler) {
		handler.onEditCancelTopic(this);
	}

}
