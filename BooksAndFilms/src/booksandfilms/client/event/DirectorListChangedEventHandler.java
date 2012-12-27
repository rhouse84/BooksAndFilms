package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DirectorListChangedEventHandler extends EventHandler {
	void onChangeDirectorList(DirectorListChangedEvent event);
}
