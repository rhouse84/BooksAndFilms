package booksandfilms.client.view;

import booksandfilms.client.entities.Director;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.DirectorPopupPresenter;

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

public class DirectorPopupView extends PopupPanel implements DirectorPopupPresenter.Display {
	@UiTemplate("DirectorPopupView.ui.xml")
	interface Binder extends UiBinder<Widget, DirectorPopupView> {}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Anchor edit;
	@UiField Label directorNameLabel;

	Director director;
	
	public DirectorPopupView() {
		// The popup's constructor's argument is a boolean specifying that
		// it auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));
	}

	public DirectorPopupView(String displayName, ClickPoint location) {
		this();
		setNameAndShow(displayName, location);
	}

	public DirectorPopupView(Director director) {
		this();
		this.director = director;
		setName(this.director.getName());
	}

	public void setName(String name) {
		directorNameLabel.setText(name);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getEditButton() {
		return edit;
	}

	public HasText getDirectorNameLabel() {
		return directorNameLabel;
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
