package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.event.TopicSelectedEvent;
import booksandfilms.client.helper.RPCCall;

public class TopicSelectPresenter implements Presenter {

	private List<Topic> topics;

	public interface Display {
		void hide();
		Widget asWidget();
		void setData(List<Topic> topics, Long topicId);
		ListBox getTopicList();
		HasClickHandlers getSaveButton();
		String getTopicValue();
		String getTopicDesc();
	}
	
	private Long topicId;
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private Display display;

	public TopicSelectPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display display, Long topicId) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.topicId = topicId;
		bind();
	}
	
	public void bind() {

		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new TopicSelectedEvent(Long.parseLong(display.getTopicValue()),display.getTopicDesc()));
			}
		});
		    
	}
	
	public void go() {
		fetchTopics();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchTopics();
	}

	private void fetchTopics() {
		new RPCCall<ArrayList<Topic>>() {
			@Override protected void callService(AsyncCallback<ArrayList<Topic>> cb) {
				rpcService.getAllTopics(cb);
			}
			
			@Override public void onSuccess(ArrayList<Topic> result) {
				topics = result;
				display.setData(topics, topicId);
				display.getTopicList();
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching topics: " + caught.getMessage());
			}
		}.retry(3);
	}
	
}
