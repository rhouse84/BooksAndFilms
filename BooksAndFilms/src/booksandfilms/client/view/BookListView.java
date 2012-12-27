package booksandfilms.client.view;

import java.util.List;

//import com.allen_sauer.gwt.log.client.Log;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.presenter.BookListPresenter;
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

public class BookListView extends Composite implements BookListPresenter.Display {

	@UiField FlexTable booksTable;
	@UiField Hyperlink addNew;
	@UiField Label loadingLabel;
	@UiField TextBox searchBox;
	@UiField Anchor sortTitle;
	@UiField Anchor sortAuthor;
	@UiField Anchor sortRating;
	@UiField Anchor sortGenre;
	@UiField Anchor sortReadDate;
	@UiField ListBox authorsField;
	@UiField ScrollPanel scrollPanel;
	
	GlobalResources globalStyles = GWT.create(GlobalResources.class);
	static BookListUiBinder uiBinder = GWT.create(BookListUiBinder.class);

	interface BookListUiBinder extends UiBinder<Widget, BookListView> {}

	private static final int HeaderRowIndex = 0;
	//If I add another column, change the value below to the new image row number.
	//These two values are also referenced in BookListPresenter
	private static final int imageColumnNumber = 9;
	private static final int authorColumnNumber = 2;
	
	public BookListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void displayBooks(List<Book> books) {
		booksTable.clear();
		booksTable.removeAllRows();
		int row = 0;
		booksTable.insertRow(HeaderRowIndex);
		//addNew.setVisible(false);

		if (books == null || books.size() == 0) {
			loadingLabel.setText("No books.");
			return;
		}
		
		loadingLabel.setVisible(false);
		boolean adminUser = getAdmin();
		
		//loop the array list and user getters to add 
		//records to the table
		for (Book book : books) {
			booksTable.setText(row, 0, book.getTitle());
			booksTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableTitle());
			booksTable.setText(row, 1, book.getAuthorName());
			booksTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableAuthor());
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName(globalStyles.css().pointer());
				booksTable.setWidget(row, authorColumnNumber, propertyImg);
				booksTable.getCellFormatter().addStyleName(row, authorColumnNumber,globalStyles.css().updateCell());
			}
			booksTable.setText(row, 3, book.getRatingDesc());
			booksTable.getCellFormatter().addStyleName(row, 3,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 3,globalStyles.css().flexTableRating());
			booksTable.setText(row, 4, book.getNotes());
			booksTable.getCellFormatter().addStyleName(row, 4,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 4,globalStyles.css().flexTableNotes());
			booksTable.setText(row, 5, book.getGenre());
			booksTable.getCellFormatter().addStyleName(row, 5,globalStyles.css().flexTableCell2());
			booksTable.getCellFormatter().addStyleName(row, 5,globalStyles.css().flexTableGenre());
			DateTimeFormat formatter = DateTimeFormat.getFormat("dd-MMM-yyyy");	
			String strReadDate = formatter.format(book.getReadDate());
			booksTable.setText(row, 6, strReadDate);
			booksTable.getCellFormatter().addStyleName(row, 6,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 6,globalStyles.css().flexTableDate());
			String strCreateDate = formatter.format(book.getCreateDate());
			booksTable.setText(row, 7, strCreateDate);
			booksTable.getCellFormatter().addStyleName(row, 7,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 7,globalStyles.css().flexTableDate());
			booksTable.setText(row, 8, book.getTopicDesc());
			booksTable.getCellFormatter().addStyleName(row, 8,globalStyles.css().flexTableCell());
			booksTable.getCellFormatter().addStyleName(row, 8,globalStyles.css().labelTitle());
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName(globalStyles.css().pointer());
				booksTable.setWidget(row, imageColumnNumber, propertyImg);
				booksTable.getCellFormatter().addStyleName(row, imageColumnNumber,globalStyles.css().updateCell());
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
		HTMLTable.Cell cell = booksTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() == imageColumnNumber || cell.getCellIndex() == authorColumnNumber) {
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
		HTMLTable.Cell cell = booksTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() == imageColumnNumber) {
				selectedRow = cell.getRowIndex();
				img = (Image) booksTable.getWidget(selectedRow, imageColumnNumber);
				
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top-40, left-180);
			} else if (cell.getCellIndex() == authorColumnNumber) {
				selectedRow = cell.getRowIndex();
				img = (Image) booksTable.getWidget(selectedRow, authorColumnNumber);
				
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top-40, left-180);
			}
		}

		return point;
	}

	@Override
	public HasClickHandlers getList() {
		return booksTable;
	}

	@Override
	public void setData(List<Book> data) {
		displayBooks(data);
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
	public void showAddBook() {
		if (getAdmin()) {
			addNew.setVisible(true);
		}
	}

	@Override
	public void hideAddBook() {
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
	public HasClickHandlers getSortAuthorButton() {
		return sortAuthor;
	}

	@Override
	public HasClickHandlers getSortRatingButton() {
		return sortRating;
	}

	@Override
	public HasClickHandlers getSortGenreButton() {
		return sortGenre;
	}

	@Override
	public HasClickHandlers getSortReadDateButton() {
		return sortReadDate;
	}

	@Override
	public void setAuthors(List<Author> authors) {
		authorsField.clear();
		authorsField.addItem("All Authors");
		authorsField.addItem("Add New...");
		for (Author author : authors) {
			authorsField.addItem(author.getName(),author.getId().toString());
		}
	}

	@Override
	public HasChangeHandlers getAuthorFilter() {
		return authorsField;
	}

	@Override
	public String getAuthorValue() {
    	if (authorsField.getSelectedIndex() > 0) {
    		return authorsField.getValue(authorsField.getSelectedIndex());
    	} else {
    		return null;
    	}
	}

	@Override
	public void setScrollHeight(int parentHeight) {
		scrollPanel.setHeight(parentHeight - 64+"px");
	}

}
