package booksandfilms.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowUserListEvent extends GwtEvent<ShowUserListEventHandler> {
	public static Type<ShowUserListEventHandler> TYPE = new Type<ShowUserListEventHandler>();
	public ShowUserListEvent() {}

	@Override
	public Type<ShowUserListEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowUserListEventHandler handler) {
		handler.onShowUserList(this);
	}

}
