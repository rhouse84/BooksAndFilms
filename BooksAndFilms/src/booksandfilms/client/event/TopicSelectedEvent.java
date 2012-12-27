package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class TopicSelectedEvent extends GwtEvent<TopicSelectedEventHandler> {
	public static Type<TopicSelectedEventHandler> TYPE = new Type<TopicSelectedEventHandler>();
	private final Long topicId;
	private final String topicDesc;
	
	public TopicSelectedEvent(Long topicId, String topicDesc) {
		this.topicId = topicId;
		this.topicDesc = topicDesc;
	}
	
	public Long getTopicId() {
		return topicId;
	}

	public String getTopicDesc() {
		return topicDesc;
	}

	@Override
	public Type<TopicSelectedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopicSelectedEventHandler handler) {
		handler.onTopicSelected(this);
	}

}
