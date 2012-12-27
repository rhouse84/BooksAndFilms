package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DirectorUpdatedEventHandler extends EventHandler {
	void onUpdateDirector(DirectorUpdatedEvent event);
}
