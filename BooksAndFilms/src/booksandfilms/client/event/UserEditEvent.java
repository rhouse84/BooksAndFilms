package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UserEditEvent extends GwtEvent<UserEditEventHandler> {
	public static Type<UserEditEventHandler> TYPE = new Type<UserEditEventHandler>();
	private final Long id;
	
	public UserEditEvent(Long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public Type<UserEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserEditEventHandler handler) {
		handler.onEditUser(this);
	}

}
