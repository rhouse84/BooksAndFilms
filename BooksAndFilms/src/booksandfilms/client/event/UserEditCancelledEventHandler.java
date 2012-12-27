package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UserEditCancelledEventHandler extends EventHandler {
	void onEditCancelUser(UserEditCancelledEvent event);
}
