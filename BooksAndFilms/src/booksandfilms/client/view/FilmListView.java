package booksandfilms.client.view;

import java.util.List;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.FilmListPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FilmListView extends Composite implements FilmListPresenter.Display {

	@UiField FlexTable filmsTable;
	@UiField Hyperlink addNew;
	@UiField Label loadingLabel;
	@UiField TextBox searchBox;
	@UiField Anchor sortTitle;
	@UiField Anchor sortDirector;
	@UiField Anchor sortRating;
	@UiField Anchor sortYear;
	@UiField Anchor sortWatchDate;
	@UiField ListBox directorsField;
	@UiField ScrollPanel scrollPanel;

	GlobalResources globalStyles = GWT.create(GlobalResources.class);
    private static FilmListUiBinder uiBinder = GWT.create(FilmListUiBinder.class);

	interface FilmListUiBinder extends UiBinder<Widget, FilmListView> {}

	private static final int HeaderRowIndex = 0;
	//If I add another column, change the value below to the new image row number.
	private static final int imageColumnNumber = 10;
	private static final int directorColumnNumber = 2;
	
	public FilmListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void displayFilms(List<Film> films) {
		filmsTable.clear();
		filmsTable.removeAllRows();
		int row = 0;
		filmsTable.insertRow(HeaderRowIndex);
		//addNew.setVisible(false);

		if (films == null || films.size() == 0) {
			loadingLabel.setText("No films.");
			return;
		}
		
		loadingLabel.setVisible(false);
//		sortTitle.addStyleName(globalStyles.css().labelPadding());
//		sortTitle.addStyleName(globalStyles.css().labelFilmTitle());
//		sortTitle.addStyleName(globalStyles.css().clearLeft());
//		sortDirector.addStyleName(globalStyles.css().labelPadding());
//		sortDirector.addStyleName(globalStyles.css().labelDirector());
//		sortRating.addStyleName(globalStyles.css().labelPadding());
//		sortRating.addStyleName(globalStyles.css().labelFilmRating());
//		sortYear.addStyleName(globalStyles.css().labelPadding());
//		sortYear.addStyleName(globalStyles.css().labelYear());
//		sortWatchDate.addStyleName(globalStyles.css().labelPadding());
//		sortWatchDate.addStyleName(globalStyles.css().labelFilmDate());
		boolean adminUser = getAdmin();
		
		//loop the array list and user getters to add 
		//records to the table
		for (Film film : films) {
			filmsTable.setText(row, 0, film.getTitle());
			filmsTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableTitle());
			filmsTable.setText(row, 1, film.getDirectorName());
			filmsTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableDirector());
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName(globalStyles.css().pointer());
				filmsTable.setWidget(row, directorColumnNumber, propertyImg);
				filmsTable.getCellFormatter().addStyleName(row, directorColumnNumber,globalStyles.css().updateCell());
			}
			filmsTable.setText(row, 3, Integer.toString(film.getYear()));
			filmsTable.getCellFormatter().addStyleName(row, 3,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 3,globalStyles.css().labelFilmYear());
			filmsTable.setText(row, 4, film.getRatingDesc());
			filmsTable.getCellFormatter().addStyleName(row, 4,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 4,globalStyles.css().labelFilmRating());
			filmsTable.setText(row, 5, film.getNotes());
			filmsTable.getCellFormatter().addStyleName(row, 5,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 5,globalStyles.css().flexTableNotes());
			filmsTable.setText(row, 6, film.getStars());
			filmsTable.getCellFormatter().addStyleName(row, 6,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 6,globalStyles.css().flexTableStars());
			DateTimeFormat formatter = DateTimeFormat.getFormat("dd-MMM-yyyy");	
			String strWatchDate = formatter.format(film.getWatchDate());
			filmsTable.setText(row, 7, strWatchDate);
			filmsTable.getCellFormatter().addStyleName(row, 7,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 7,globalStyles.css().flexTableFilmDate());
			String strCreateDate = formatter.format(film.getCreateDate());
			filmsTable.setText(row, 8, strCreateDate);
			filmsTable.getCellFormatter().addStyleName(row, 8,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 8,globalStyles.css().flexTableFilmDate());
			filmsTable.setText(row, 9, film.getTopicDesc());
			filmsTable.getCellFormatter().addStyleName(row, 9,globalStyles.css().flexTableCell());
			filmsTable.getCellFormatter().addStyleName(row, 9,globalStyles.css().labelTopic());
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName(globalStyles.css().pointer());
				filmsTable.setWidget(row, imageColumnNumber, propertyImg);
			}
			row++;
		}

	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return addNew;
	}

	@Override
	public int[] getClickedRow(ClickEvent event) {
		int[] cellValues = new int[2];
		int selectedRow = -1;
		HTMLTable.Cell cell = filmsTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() == imageColumnNumber || cell.getCellIndex() == directorColumnNumber) {
				selectedRow = cell.getRowIndex();
			}
		}

		cellValues[0] = selectedRow;
		cellValues[1] = cell.getCellIndex();
		return cellValues;
	}

	@Override
	public ClickPoint getClickedPoint(ClickEvent event) {
		final Image img;
		int selectedRow = -1;
		ClickPoint point = null;
		HTMLTable.Cell cell = filmsTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() == imageColumnNumber) {
				selectedRow = cell.getRowIndex();
				img = (Image) filmsTable.getWidget(selectedRow, imageColumnNumber);
				
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top-40, left-180);
			} else if (cell.getCellIndex() == directorColumnNumber) {
				selectedRow = cell.getRowIndex();
				img = (Image) filmsTable.getWidget(selectedRow, directorColumnNumber);
				
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top-40, left-180);
			}
		}

		return point;
	}

	@Override
	public HasClickHandlers getList() {
		return filmsTable;
	}

	@Override
	public void setData(List<Film> data) {
		displayFilms(data);
	}

	@Override
	public HasKeyPressHandlers getSearchBox() {
		return searchBox;
	}

	@Override
	public String getSearchValue() {
		return searchBox.getText();
	}

	@Override
	public void showAddFilm() {
		if (getAdmin()) {
			addNew.setVisible(true);
		}
	}

	@Override
	public void hideAddFilm() {
		addNew.setVisible(false);
	}

	@Override
	public boolean getAdmin() {
		return BooksAndFilms.get().getCurrentUser().getAdminUser();
	}

	@Override
	public HasClickHandlers getSortTitleButton() {
		return sortTitle;
	}

	@Override
	public HasClickHandlers getSortDirectorButton() {
		return sortDirector;
	}

	@Override
	public HasClickHandlers getSortRatingButton() {
		return sortRating;
	}

	@Override
	public HasClickHandlers getSortYearButton() {
		return sortYear;
	}

	@Override
	public HasClickHandlers getSortWatchDateButton() {
		return sortWatchDate;
	}

	@Override
	public void setDirectors(List<Director> directors) {
		directorsField.clear();
		directorsField.addItem("All Directors");
		directorsField.addItem("Add New...");
		for (Director director : directors) {
			directorsField.addItem(director.getName(),director.getId().toString());
		}
	}

	@Override
	public HasChangeHandlers getDirectorFilter() {
		return directorsField;
	}

	@Override
	public String getDirectorValue() {
    	if (directorsField.getSelectedIndex() > 0) {
    		return directorsField.getValue(directorsField.getSelectedIndex());
    	} else {
    		return null;
    	}
	}

	@Override
	public void setScrollHeight(int parentHeight) {
		scrollPanel.setHeight(parentHeight - 64+"px");
	}

}
