package booksandfilms.client.view;

import java.util.ArrayList;
import java.util.List;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.presenter.DirectorListPresenter;
import booksandfilms.client.entities.Director;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DirectorListView extends Composite implements DirectorListPresenter.Display {

	@UiField FlexTable directorsTable;
	@UiField Hyperlink addNew;
	@UiField Label loadingLabel;
	@UiField TextBox searchBox;
	
	private List<String> selectedNames = new ArrayList<String>();
	
	private static DirectorListUiBinder uiBinder = GWT.create(DirectorListUiBinder.class);

	interface DirectorListUiBinder extends UiBinder<Widget, DirectorListView> {}
	
	public DirectorListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void displayDirectors(List<Director> directors) {
		directorsTable.clear();
		int row = 0;
		directorsTable.removeAllRows();
		
		if (directors == null || directors.size() == 0) {
			loadingLabel.setText("No directors.");
			return;
		}
		
		loadingLabel.setVisible(false);
		boolean adminUser = getAdmin();
		List<String> seen = new ArrayList<String>();
		
		//loop the array list and user getters to add 
		//records to the table
		for (Director director : directors) {
			String name = truncateLongName(director.getName());
			CheckBox checkBoxName = new CheckBox(truncateLongName(name));

			if(selectedNames.contains(truncateLongName(name))) { 
				checkBoxName.setValue(true);
				seen.add(name);
			} else {
				checkBoxName.setValue(false);
			}

			directorsTable.setWidget(row, 0, checkBoxName);
			if (adminUser) { 
				final Image propertyImg = new Image(GlobalResources.RESOURCE.propertyButton());
				propertyImg.setStyleName("pointer");
				directorsTable.setWidget(row, 1, propertyImg);
			}
			row++;
		}
		if (!adminUser) {
			addNew.setVisible(false);
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
		HTMLTable.Cell cell = directorsTable.getCellForEvent(event);

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
		HTMLTable.Cell cell = directorsTable.getCellForEvent(event);

		if (cell != null) {
			// Suppress clicks if not on the property button
			if (cell.getCellIndex() > 0) {
				selectedRow = cell.getRowIndex();
				img = (Image) directorsTable.getWidget(selectedRow, 1);
				int left = img.getAbsoluteLeft();
				int top = img.getAbsoluteTop();
				point = new ClickPoint(top, left);
			}
		}

		return point;
	}

	@Override
	public HasClickHandlers getList() {
		return directorsTable;
	}

	@Override
	public List<Integer> getSelectedRows() {
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		ArrayList<String> selected = new ArrayList<String>();

		for (int i = 0; i < directorsTable.getRowCount(); ++i) {
			CheckBox checkBox = (CheckBox) directorsTable.getWidget(i, 0);
			if (checkBox.getValue()) {
				selectedRows.add(i);
				selected.add(checkBox.getHTML());
			} 
		}
	    
		selectedNames = selected;
		return selectedRows;
	}

	@Override
	public void setData(List<Director> data) {
		displayDirectors(data);
	}

	@Override
	public HasKeyPressHandlers getSearchBox() {
		return searchBox;
	}

	@Override
	public String getSearchValue() {
		selectedNames.clear();
		return searchBox.getText();
	}

	@Override
	public boolean getAdmin() {
		return BooksAndFilms.get().getCurrentUser().getAdminUser();
	}

}
