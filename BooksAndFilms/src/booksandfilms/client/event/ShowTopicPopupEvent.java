package booksandfilms.client.event;

import booksandfilms.client.entities.Topic;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.GwtEvent;

public class ShowTopicPopupEvent extends GwtEvent<ShowTopicPopupEventHandler> {
	public static Type<ShowTopicPopupEventHandler> TYPE = new Type<ShowTopicPopupEventHandler>();
	private final Topic Topic;
	private final ClickPoint point;
	
	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowTopicPopupEvent(Topic Topic, ClickPoint point) {
		this.Topic = Topic;
		this.point = point;
	}

	public Topic getTopic() {
		return Topic;
	}

	@Override
	public Type<ShowTopicPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowTopicPopupEventHandler handler) {
		handler.onShowTopicPopup(this);
	}

}
