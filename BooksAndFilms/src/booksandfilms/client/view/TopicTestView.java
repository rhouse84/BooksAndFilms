package booksandfilms.client.view;

import java.util.List;

import booksandfilms.client.presenter.TopicTestPresenter;
import booksandfilms.client.widget.ListItemWidget;
import booksandfilms.client.widget.UnorderedListWidget;
import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.entities.Topic;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TopicTestView extends Composite implements TopicTestPresenter.Display {

	@UiField UnorderedListWidget topicsTable;
	@UiField Hyperlink addNew;
	@UiField Label loadingLabel;
	@UiField TextBox searchBox;
	@UiField Button searchButton;
	
	GlobalResources globalStyles = GWT.create(GlobalResources.class);
	private static TopicListUiBinder uiBinder = GWT.create(TopicListUiBinder.class);

	interface TopicListUiBinder extends UiBinder<Widget, TopicTestView> {}
	
	public TopicTestView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void displayTopics(List<Topic> topics) {
		topicsTable.clear();
		
		if (topics == null || topics.size() == 0) {
			loadingLabel.setText("No topics.");
			return;
		}
		
		loadingLabel.setVisible(false);
		
		//loop the array list and user getters to add 
		//records to the table
		for (Topic topic : topics) {
			//topicsTable.add(new ListItemWidget(topic.getId().toString()));
			final ListItemWidget liw = new ListItemWidget(topic.getDescription()).addClass(globalStyles.css().flexTableCell()).addClass("SecondClass");
			//topicsTable.add(new ListItemWidget(topic.getDescription()).addClass(globalStyles.css().flexTableCell()).addClass("SecondClass"));
			liw.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Window.alert("Hello, again ");
				}
			});
			topicsTable.add(liw);
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
	public UnorderedListWidget getList() {
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

	@Override
	public HasClickHandlers getSearchButton() {
		return searchButton;
	}

	@Override
	public int[] getTopLeft() {
		int[] topLeft = new int[2];
		topLeft[0] = searchButton.getAbsoluteTop();
		topLeft[1] = searchButton.getAbsoluteLeft();
		return topLeft;
	}


}
