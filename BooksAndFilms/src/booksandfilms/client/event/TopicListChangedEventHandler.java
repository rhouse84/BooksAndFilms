package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface TopicListChangedEventHandler extends EventHandler {
	void onChangeTopicList(TopicListChangedEvent event);
}
