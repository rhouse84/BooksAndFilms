package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class TopicUpdatedEvent extends GwtEvent<TopicUpdatedEventHandler> {
	public static Type<TopicUpdatedEventHandler> TYPE = new Type<TopicUpdatedEventHandler>();

	@Override
	public Type<TopicUpdatedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopicUpdatedEventHandler handler) {
		handler.onUpdateTopic(this);
	}

}
