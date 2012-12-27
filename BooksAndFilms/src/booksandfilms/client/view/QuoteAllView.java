package booksandfilms.client.view;

import java.util.List;

import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.presenter.QuoteAllPresenter;
import booksandfilms.client.entities.QuoteAll;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class QuoteAllView extends Composite implements QuoteAllPresenter.Display {

	@UiField FlexTable quotesTable;
	@UiField Label loadingLabel;
	//@UiField TextBox searchBox;
	@UiField ScrollPanel scrollPanel;
	
	GlobalResources globalStyles = GWT.create(GlobalResources.class);
	private static QuoteListUiBinder uiBinder = GWT.create(QuoteListUiBinder.class);

	interface QuoteListUiBinder extends UiBinder<Widget, QuoteAllView> {}
	
	private static final int HeaderRowIndex = 0;

	public QuoteAllView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void displayQuotes(List<QuoteAll> quotes) {
		quotesTable.clear();
		quotesTable.removeAllRows();
		int row = 0;
		quotesTable.insertRow(HeaderRowIndex);
		
		if (quotes == null || quotes.size() == 0) {
			loadingLabel.setText("No quotes.");
			return;
		}
		
		loadingLabel.setVisible(false);
		
		for (QuoteAll quote : quotes) {
			if (quote.getType().equalsIgnoreCase("book")) {
				quotesTable.setText(row, 0, quote.getTitle());
				quotesTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableCell2());
				quotesTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().labelQuoteTitle());
				quotesTable.setText(row, 1, "");
				quotesTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableCell2());
				quotesTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().labelQuoteTitle());
			} else {
				quotesTable.setText(row, 0, "");
				quotesTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().flexTableCell2());
				quotesTable.getCellFormatter().addStyleName(row, 0,globalStyles.css().labelQuoteTitle());
				quotesTable.setText(row, 1, quote.getTitle());
				quotesTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().flexTableCell2());
				quotesTable.getCellFormatter().addStyleName(row, 1,globalStyles.css().labelQuoteTitle());
			}
			quotesTable.setText(row, 2, quote.getCharacterName());
			quotesTable.getCellFormatter().addStyleName(row, 2,globalStyles.css().flexTableCell2());
			quotesTable.getCellFormatter().addStyleName(row, 2,globalStyles.css().labelQuoteTitle());
			quotesTable.setText(row, 3, quote.getQuoteText());
			quotesTable.getCellFormatter().addStyleName(row, 3,globalStyles.css().flexTableCell2());
			quotesTable.getCellFormatter().addStyleName(row, 3,globalStyles.css().flexTableQuote());
			row++;
		}

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return null;
	}

	@Override
	public int getClickedRow(ClickEvent event) {
		return (Integer) null;
	}

	@Override
	public ClickPoint getClickedPoint(ClickEvent event) {
		return null;
	}

//	private void addColumn(Object columnHeading) {
//		Widget widget = createCellWidget(columnHeading);
//		int cell = quotesTable.getCellCount(HeaderRowIndex);
//		widget.setWidth("100%");
//		widget.addStyleName("flexTableColumnLabel");
//		quotesTable.setWidget(HeaderRowIndex, cell, widget);
//		quotesTable.getCellFormatter().addStyleName(HeaderRowIndex, cell, globalStyles.css().flexTableColumnLabelCell());
//	}
//	
//	private Widget createCellWidget(Object cellObject) {
//		Widget widget = null;
//		if (cellObject instanceof Widget)
//			widget = (Widget) cellObject;
//		else
//			widget = new Label(cellObject.toString());
//		return widget;
//	}
	
	@Override
	public HasClickHandlers getList() {
		return quotesTable;
	}

	@Override
	public void setData(List<QuoteAll> data) {
		displayQuotes(data);
	}

	@Override
	public void setScrollHeight(int parentHeight) {
		scrollPanel.setHeight(parentHeight - 32+"px");
	}

//	@Override
//	public HasKeyPressHandlers getSearchBox() {
//		return searchBox;
//	}
//
//	@Override
//	public String getSearchValue() {
//		return searchBox.getText();
//	}


}
