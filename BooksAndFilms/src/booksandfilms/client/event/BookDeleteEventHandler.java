package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookDeleteEventHandler extends EventHandler {
	void onDeleteBook(BookDeleteEvent event);
}
