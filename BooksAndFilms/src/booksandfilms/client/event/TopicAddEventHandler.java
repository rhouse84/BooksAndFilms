package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface TopicAddEventHandler extends EventHandler {
	void onAddTopic(TopicAddEvent event);
}
