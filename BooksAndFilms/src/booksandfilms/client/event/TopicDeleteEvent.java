package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class TopicDeleteEvent extends GwtEvent<TopicDeleteEventHandler> {
	public static Type<TopicDeleteEventHandler> TYPE = new Type<TopicDeleteEventHandler>();
	
	@Override
	public Type<TopicDeleteEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopicDeleteEventHandler handler) {
		handler.onDeleteTopic(this);	
	}

}
