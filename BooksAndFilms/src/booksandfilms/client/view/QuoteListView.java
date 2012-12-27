package booksandfilms.client.view;

import java.util.List;

import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.presenter.QuoteListPresenter;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class QuoteListView extends Composite implements QuoteListPresenter.Display {

	@UiField FlexTable quotesTable;
	@UiField Hyperlink addNew;
	@UiField Label loadingLabel;
	@UiField TextBox searchBox;
	
	GlobalResources globalStyles = GWT.create(GlobalResources.class);
	private static QuoteListUiBinder uiBinder = GWT.create(QuoteListUiBinder.class);

	interface QuoteListUiBinder extends UiBinder<Widget, QuoteListView> {}
	
	private static boolean adminUser = true;
	private static final int HeaderRowIndex = 0;
	//If I add another column, change the value below to the new image row number.
	private static final int imageColumnNumber = 2;

	public QuoteListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void displayQuotes(List<Quote> quotes) {
		quotesTable.clear();
		quotesTable.removeAllRows();
		int row = 1;
		quotesTable.insertRow(HeaderRowIndex);
		
		if (quotes == null || quotes.size() == 0) {
			loadingLabel.setText("No quotes.");
			return;
		}
		
		loadingLabel.setVisible(false);
		
		addColumn("Character");
		addColumn("Quote");

		//loop the array list and user getters to add 
		//records to the table
		for (Quote quote : quotes) {
			quotesTable.setText(row, 0, quote.getCharacterName());
			quotesTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableCell());
			quotesTable.setText(row, 1, quote.getQuoteText());
			quotesTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableCell());
			quotesTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableQuote());
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName(globalStyles.css().pointer());
				quotesTable.setWidget(row, imageColumnNumber, propertyImg);
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
	public int getClickedRow(ClickEvent event) {
		int selectedRow = -1;
		HTMLTable.Cell cell = quotesTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() == imageColumnNumber) {
				selectedRow = cell.getRowIndex()-1;
			}
		}

		return selectedRow;
	}

	@Override
	public ClickPoint getClickedPoint(ClickEvent event) {
		final Image img;
		int selectedRow = -1;
		ClickPoint point = null;
		HTMLTable.Cell cell = quotesTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() == imageColumnNumber) {
				selectedRow = cell.getRowIndex();
				img = (Image) quotesTable.getWidget(selectedRow, imageColumnNumber);
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top, left);
			}
		}

		return point;
	}

	private void addColumn(Object columnHeading) {
		Widget widget = createCellWidget(columnHeading);
		int cell = quotesTable.getCellCount(HeaderRowIndex);
		widget.setWidth("100%");
		widget.addStyleName("flexTableColumnLabel");
		quotesTable.setWidget(HeaderRowIndex, cell, widget);
		quotesTable.getCellFormatter().addStyleName(HeaderRowIndex, cell, globalStyles.css().flexTableColumnLabelCell());
	}
	
	private Widget createCellWidget(Object cellObject) {
		Widget widget = null;
		if (cellObject instanceof Widget)
			widget = (Widget) cellObject;
		else
			widget = new Label(cellObject.toString());
		return widget;
	}
	
	@Override
	public HasClickHandlers getList() {
		return quotesTable;
	}

	@Override
	public void setData(List<Quote> data) {
		displayQuotes(data);
	}

	@Override
	public HasKeyPressHandlers getSearchBox() {
		return searchBox;
	}

	@Override
	public String getSearchValue() {
		return searchBox.getText();
	}


}
