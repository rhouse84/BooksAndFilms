package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Topic;

import com.google.gwt.event.shared.GwtEvent;

public class TopicListChangedEvent extends	GwtEvent<TopicListChangedEventHandler> {
	public static Type<TopicListChangedEventHandler> TYPE = new Type<TopicListChangedEventHandler>();
	private final List<Topic> Topics;
	private final List<Topic> someTopics;

	public TopicListChangedEvent (List<Topic> list, List<Topic> all) {
		this.someTopics = list;
		this.Topics = all;
	}
	
	public List<Topic> getAllTopics(){ return Topics; }
	public List<Topic> getSelectTopics() { return someTopics; }
	
	@Override
	public Type<TopicListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TopicListChangedEventHandler handler) {
		handler.onChangeTopicList(this);
	}

}
