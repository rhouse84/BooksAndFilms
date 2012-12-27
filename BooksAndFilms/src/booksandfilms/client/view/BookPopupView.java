package booksandfilms.client.view;

import booksandfilms.client.entities.Book;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.BookPopupPresenter;

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

public class BookPopupView extends PopupPanel implements BookPopupPresenter.Display {

	@UiTemplate("BookPopupView.ui.xml")
	interface Binder extends UiBinder<Widget, BookPopupView> {}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Anchor edit, delete;
	@UiField Label bookTitleLabel;

	Book book;
	
	public BookPopupView() {
		// The popup's constructor's argument is a boolean specifying that
		// it auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));
	}

	public BookPopupView(String displayName, ClickPoint location) {
		this();
		setNameAndShow(displayName, location);
	}

	public BookPopupView(Book book) {
		this();
		this.book = book;
		setTitle(this.book.getTitle());
	}

	public void setTitle(String title) {
		bookTitleLabel.setText(title);
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

	public HasText getBookTitleLabel() {
		return bookTitleLabel;
	}

	@Override
	public void hide() {
		super.hide();
	}

	public void setNameAndShow(String displayName, ClickPoint location) {
		setTitle(displayName);
		setPopupPosition(location.getLeft(), location.getTop());
		show();
	}
}
