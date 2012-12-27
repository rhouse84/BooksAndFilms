package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface TopicSelectedEventHandler extends EventHandler {
	void onTopicSelected(TopicSelectedEvent event);
}
