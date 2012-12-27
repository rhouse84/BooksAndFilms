package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.List;

//import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.event.ShowMediaGetEvent;
import booksandfilms.client.event.TopicAddEvent;
import booksandfilms.client.event.TopicDeleteEvent;
import booksandfilms.client.event.TopicDeleteEventHandler;
import booksandfilms.client.event.TopicUpdatedEvent;
import booksandfilms.client.event.TopicUpdatedEventHandler;
import booksandfilms.client.helper.RPCCall;
import booksandfilms.client.widget.UnorderedListWidget;
import booksandfilms.shared.SharedConstants;

public class TopicTestPresenter implements Presenter {

	private List<Topic> topics;
	private List<Topic> someTopics = new ArrayList<Topic>();
	
	public interface Display {
		HasClickHandlers getAddButton();
		UnorderedListWidget getList();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		void setData(List<Topic> topicNames);
		Widget asWidget();
		HasClickHandlers getSearchButton();
		int[] getTopLeft();
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	
	public TopicTestPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		bind();
	}
	
	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new TopicAddEvent());
			}
		});
		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					searchBoxChange();
				}
			}
		});
		this.display.getSearchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int[] topLeft = display.getTopLeft();
				eventBus.fireEvent(new ShowMediaGetEvent(topLeft, SharedConstants.AUTHOR));
			}
		});

		
		// Listen to events
		eventBus.addHandler(TopicUpdatedEvent.TYPE, new TopicUpdatedEventHandler() {
	    	@Override public void onUpdateTopic(TopicUpdatedEvent event) {
	        	fetchTopicSummary();
	    	}
	    });

		eventBus.addHandler(TopicDeleteEvent.TYPE, new TopicDeleteEventHandler() {
			@Override public void onDeleteTopic(TopicDeleteEvent event) {
				fetchTopicSummary();
			}
		});

	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchTopicSummary();
	}

	public void setTopicSummary(List<Topic> topics) {
		this.topics = topics;
	}
	
	public Topic getTopicSummary(int index) {
		return topics.get(index);
	}
	
	private void fetchTopicSummary() {
		new RPCCall<ArrayList<Topic>>() {
			@Override protected void callService(AsyncCallback<ArrayList<Topic>> cb) {
				rpcService.getAllTopics(cb);
			}
			
			@Override public void onSuccess(ArrayList<Topic> result) {
				topics = result;
				display.setData(result);
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching topic summaries: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void searchBoxChange() {
		String searchString = display.getSearchValue();
		someTopics.clear();
		if (searchString.isEmpty()) {
			display.setData(topics);
		} else {
			for (Topic a : topics) {
				if (a.getDescription().toUpperCase().startsWith(searchString.toUpperCase()))
					someTopics.add(a);
			}
			display.setData(someTopics);
		}
	}
}
