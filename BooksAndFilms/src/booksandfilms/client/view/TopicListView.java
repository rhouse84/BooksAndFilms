package booksandfilms.client.view;

import java.util.List;

import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.presenter.TopicListPresenter;
import booksandfilms.client.entities.Topic;
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

public class TopicListView extends Composite implements TopicListPresenter.Display {

	@UiField FlexTable topicsTable;
	@UiField Hyperlink addNew;
	@UiField Label loadingLabel;
	@UiField TextBox searchBox;
	
	private static TopicListUiBinder uiBinder = GWT.create(TopicListUiBinder.class);

	interface TopicListUiBinder extends UiBinder<Widget, TopicListView> {}
	
	private static boolean adminUser = true;

	public TopicListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void displayTopics(List<Topic> topics) {
		topicsTable.clear();
		int row = 0;
		topicsTable.removeAllRows();
		
		if (topics == null || topics.size() == 0) {
			loadingLabel.setText("No topics.");
			return;
		}
		
		loadingLabel.setVisible(false);
		
		//loop the array list and user getters to add 
		//records to the table
		for (Topic topic : topics) {
			String name = truncateLongName(topic.getDescription());

			topicsTable.setText(row, 0, name);
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName("pointer");
				topicsTable.setWidget(row, 1, propertyImg);
			}
			row++;
		}

	}

	private String truncateLongName(String displayName) {
		final int MAX = 26; // truncate string if longer than MAX
		final String SUFFIX = "...";

		if (displayName.length() < MAX)
			return displayName;

		String shortened = displayName.substring(0, MAX - SUFFIX.length()) + SUFFIX;

		return shortened;
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
		HTMLTable.Cell cell = topicsTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() > 0) {
				selectedRow = cell.getRowIndex();
			}
		}

		return selectedRow;
	}

	@Override
	public ClickPoint getClickedPoint(ClickEvent event) {
		final Image img;
		int selectedRow = -1;
		ClickPoint point = null;
		HTMLTable.Cell cell = topicsTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() > 0) {
				selectedRow = cell.getRowIndex();
				img = (Image) topicsTable.getWidget(selectedRow, 1);
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top, left);
			}
		}

		return point;
	}

	@Override
	public HasClickHandlers getList() {
		return topicsTable;
	}

	@Override
	public void setData(List<Topic> data) {
		displayTopics(data);
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
