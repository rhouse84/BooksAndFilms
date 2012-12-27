package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookEditEventHandler extends EventHandler {
	void onEditBook(BookEditEvent event);
}
