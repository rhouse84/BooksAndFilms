package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowTopicSelectEvent extends GwtEvent<ShowTopicSelectEventHandler> {
	public static Type<ShowTopicSelectEventHandler> TYPE = new Type<ShowTopicSelectEventHandler>();
	private final Long topicId;
	
	public ShowTopicSelectEvent(Long topicId) {
		this.topicId = topicId;
	}
	
	public Long getTopicId() {
		return topicId;
	}

	@Override
	public Type<ShowTopicSelectEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowTopicSelectEventHandler handler) {
		handler.onShowTopicSelect(this);
	}

}
