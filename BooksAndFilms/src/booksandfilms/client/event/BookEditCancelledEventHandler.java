package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookEditCancelledEventHandler extends EventHandler {
	void onEditCancelBook(BookEditCancelledEvent event);
}
