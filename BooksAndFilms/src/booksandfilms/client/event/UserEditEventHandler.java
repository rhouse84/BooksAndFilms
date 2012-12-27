package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UserEditEventHandler extends EventHandler {
	void onEditUser(UserEditEvent event);
}
