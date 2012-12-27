package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BookListChangedEventHandler extends EventHandler {
	void onChangeBookList(BookListChangedEvent event);
}
