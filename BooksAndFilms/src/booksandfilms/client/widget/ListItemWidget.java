package booksandfilms.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ListItemWidget extends SimplePanel implements HasClickHandlers {

	public ListItemWidget() {
		super((Element) Document.get().createLIElement().cast());
	}

	public ListItemWidget(String s) {
		this();
		getElement().setInnerText(s);
	}

	public ListItemWidget(Widget w) {
		this();
		this.add(w);
	}
	
	public ListItemWidget addClass(String s) {
		getElement().addClassName(s);
		return this;
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}	
}
