package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UserDeleteEventHandler extends EventHandler {
	void onDeleteUser(UserDeleteEvent event);
}
