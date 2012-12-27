package booksandfilms.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Media;
import booksandfilms.client.presenter.MediaGetPresenter;

public class MediaGetView extends PopupPanel implements MediaGetPresenter.Display {

	@UiTemplate("MediaGetView.ui.xml")
	interface Binder extends UiBinder<Widget, MediaGetView> {}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField ListBox searchResults;
	@UiField TextBox searchBox;
	@UiField Button searchButton;

	public MediaGetView() {
		super(true);
		add(binder.createAndBindUi(this));
	}

	public MediaGetView(int[] topLeft) {
		this();
		show(topLeft);
	}

	@Override
	public HasClickHandlers getSearchButton() {
		return searchButton;
	}

	@Override
	public HasKeyPressHandlers getSearchBox() {
		return searchBox;
	}

	@Override
	public String getSearchValue() {
		return searchBox.getText();
	}

	public void show(int[] topLeft) {
		setPopupPosition(topLeft[0], topLeft[1]);
		show();
		searchBox.setFocus(true);
	}

	@Override
	public void setSearchResults(ArrayList<Media> resultList) {
		for (Media media : resultList) {
			if (media instanceof Author) {
				Author author = new Author();
				author = (Author) media;
				searchResults.addItem(author.getName());
			} else {
				Director director = new Director();
				director = (Director) media;
				searchResults.addItem(director.getName());
			}
		}
		
	}

}
