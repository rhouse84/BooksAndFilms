package booksandfilms.client.view;

import booksandfilms.client.entities.UserAccount;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.UserPopupPresenter;

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

public class UserPopupView extends PopupPanel implements UserPopupPresenter.Display {
	@UiTemplate("UserPopupView.ui.xml")
	interface Binder extends UiBinder<Widget, UserPopupView> {}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Anchor edit, delete;
	@UiField Label userNameLabel;

	UserAccount user;
	
	public UserPopupView() {
		// The popup's constructor's argument is a boolean specifying that
		// it auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));
	}

	public UserPopupView(String displayName, ClickPoint location) {
		this();
		setNameAndShow(displayName, location);
	}

	public UserPopupView(UserAccount user) {
		this();
		this.user = user;
		setName(this.user.getName());
	}

	public void setName(String name) {
		userNameLabel.setText(name);
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

	public HasText getUserNameLabel() {
		return userNameLabel;
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
