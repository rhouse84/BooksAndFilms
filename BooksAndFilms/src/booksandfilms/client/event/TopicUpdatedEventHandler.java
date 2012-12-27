package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface TopicUpdatedEventHandler extends EventHandler {
	void onUpdateTopic(TopicUpdatedEvent event);
}
