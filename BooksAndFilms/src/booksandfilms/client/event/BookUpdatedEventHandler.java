package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookUpdatedEventHandler extends EventHandler {
	void onUpdateBook(BookUpdatedEvent event);
}
