package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface TopicDeleteEventHandler extends EventHandler {
	void onDeleteTopic(TopicDeleteEvent event);
}
