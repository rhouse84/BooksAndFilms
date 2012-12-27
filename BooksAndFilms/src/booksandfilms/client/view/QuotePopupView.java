package booksandfilms.client.view;

import booksandfilms.client.entities.Quote;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.QuotePopupPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class QuotePopupView extends PopupPanel implements QuotePopupPresenter.Display {
	@UiTemplate("QuotePopupView.ui.xml")
	interface Binder extends UiBinder<Widget, QuotePopupView> {}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Anchor edit, delete;
	@UiField Label quoteNameLabel;

	Quote quote;
	
	public QuotePopupView() {
		// The popup's constructor's argument is a boolean specifying that
		// it auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));
	}

	public QuotePopupView(String displayName, ClickPoint location) {
		this();
		setNameAndShow(displayName, location);
	}

	public QuotePopupView(Quote quote) {
		this();
		this.quote = quote;
		setName(this.quote.getQuoteText());
	}

	public void setName(String name) {
		quoteNameLabel.setText(name);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getDeleteButton() {
		return delete;
	}

	public HasClickHandlers getEditButton() {
		return edit;
	}

	public HasText getQuoteNameLabel() {
		return quoteNameLabel;
	}

	@Override
	public void hide() {
		super.hide();
	}

	public void setNameAndShow(String displayName, ClickPoint location) {
		setName(displayName);
		setPopupPosition(location.getLeft(), location.getTop());
		show();
	}

}
