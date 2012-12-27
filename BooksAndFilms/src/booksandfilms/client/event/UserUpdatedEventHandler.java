package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UserUpdatedEventHandler extends EventHandler {
	void onUpdateUser(UserUpdatedEvent event);
}
