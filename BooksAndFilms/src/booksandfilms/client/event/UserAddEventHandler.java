package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UserAddEventHandler extends EventHandler {
	void onAddUser(UserAddEvent event);
}
