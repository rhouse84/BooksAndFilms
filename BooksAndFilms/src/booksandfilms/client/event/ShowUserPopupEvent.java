package booksandfilms.client.event;

import booksandfilms.client.entities.UserAccount;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.GwtEvent;

public class ShowUserPopupEvent extends GwtEvent<ShowUserPopupEventHandler> {
	public static Type<ShowUserPopupEventHandler> TYPE = new Type<ShowUserPopupEventHandler>();
	private final UserAccount user;
	private final ClickPoint point;
	
	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowUserPopupEvent(UserAccount user, ClickPoint point) {
		this.user = user;
		this.point = point;
	}

	public UserAccount getUser() {
		return user;
	}

	@Override
	public Type<ShowUserPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowUserPopupEventHandler handler) {
		handler.onShowUserPopup(this);
	}

}
