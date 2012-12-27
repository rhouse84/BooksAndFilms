package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AuthorListChangedEventHandler extends EventHandler {
	void onChangeAuthorList(AuthorListChangedEvent event);
}
