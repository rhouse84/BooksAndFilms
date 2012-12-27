package booksandfilms.client.presenter;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.event.TopicEditCancelledEvent;
import booksandfilms.client.event.TopicUpdatedEvent;
import booksandfilms.client.helper.RPCCall;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class TopicEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getDescription();
		HasClickHandlers getSaveButton();
		Widget asWidget();
	}
	
	private Topic topic;
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public TopicEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.topic = new Topic();
		bind();
	}
	
	public TopicEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Long id) {
		this(queryService, persistentService, eventBus, display);
	
		if (id == null)
			return;
	      
		new RPCCall<Topic>() {
			@Override
			protected void callService(AsyncCallback<Topic> cb) {
				queryService.getTopicById(id, cb);
			}

			@Override
			public void onSuccess(Topic result) {
				topic = result;
				TopicEditPresenter.this.display.getDescription().setValue(topic.getDescription());
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving friend...");
			}
		}.retry(3);

	}

	public void bind() {
		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new TopicEditCancelledEvent());
			}
		});
		    
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void doSave() {
		topic.setDescription(display.getDescription().getValue().trim());

		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				persistentService.updateTopic(topic, cb);
			}

			public void onSuccess(Void result) {
				GWT.log("TopicEditPresenter: Firing TopicUpdateEvent");
				eventBus.fireEvent(new TopicUpdatedEvent());
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving Topic...");
			}

		}.retry(3);
	}

}
