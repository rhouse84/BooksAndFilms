package booksandfilms.client.view;

import booksandfilms.client.entities.Film;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.FilmPopupPresenter;

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

public class FilmPopupView extends PopupPanel implements FilmPopupPresenter.Display {

	@UiTemplate("FilmPopupView.ui.xml")
	interface Binder extends UiBinder<Widget, FilmPopupView> {}
	
	private static final Binder binder = GWT.create(Binder.class);
	
	@UiField Anchor edit, delete;
	@UiField Label filmTitleLabel;

	Film film;
	
	public FilmPopupView() {
		// The popup's constructor's argument is a boolean specifying that
		// it auto-close itself when the user clicks outside of it.
		super(true);
		add(binder.createAndBindUi(this));
	}

	public FilmPopupView(String displayName, ClickPoint location) {
		this();
		setNameAndShow(displayName, location);
	}

	public FilmPopupView(Film film) {
		this();
		this.film = film;
		setTitle(this.film.getTitle());
	}

	public void setTitle(String title) {
		filmTitleLabel.setText(title);
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

	public HasText getFilmTitleLabel() {
		return filmTitleLabel;
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
