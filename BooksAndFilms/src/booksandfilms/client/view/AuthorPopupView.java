package booksandfilms.client.view;

import booksandfilms.client.entities.Author;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.AuthorPopupPresenter;

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

public class AuthorPopupView extends PopupPanel implements AuthorPopupPresenter.Display {
	@UiTemplate("AuthorPopupView.ui.xml")
	interface Binder extends UiBinder<Widget, AuthorPopupView> {}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Anchor edit;
	@UiField Label authorNameLabel;

	Author author;
	
	public AuthorPopupView() {
		// The popup's constructor's argument is a boolean specifying that
		// it auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));
	}

	public AuthorPopupView(String displayName, ClickPoint location) {
		this();
		setNameAndShow(displayName, location);
	}

	public AuthorPopupView(Author author) {
		this();
		this.author = author;
		setName(this.author.getName());
	}

	public void setName(String name) {
		authorNameLabel.setText(name);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getEditButton() {
		return edit;
	}

	public HasText getAuthorNameLabel() {
		return authorNameLabel;
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
