package booksandfilms.client.presenter;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Director;
import booksandfilms.client.event.DirectorEditCancelledEvent;
import booksandfilms.client.event.DirectorUpdatedEvent;
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

public class DirectorEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getName();
		HasClickHandlers getSaveButton();
		Widget asWidget();
	}
	
	private Director director;
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public DirectorEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.director = new Director();
		bind();
	}
	
	public DirectorEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Long id) {
		this(queryService, persistentService, eventBus, display);
	
		if (id == null)
			return;
	      
		new RPCCall<Director>() {
			@Override
			protected void callService(AsyncCallback<Director> cb) {
				queryService.getDirectorById(id, cb);
			}

			@Override
			public void onSuccess(Director result) {
				director = result;
				DirectorEditPresenter.this.display.getName().setValue(director.getName());
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
				if (display.getName().getValue().trim().equals(director.getName())) {
					eventBus.fireEvent(new DirectorEditCancelledEvent());
				} else {
					doSave();
				}
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new DirectorEditCancelledEvent());
			}
		});
		    
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void doSave() {
		director.setName(display.getName().getValue().trim());

		new RPCCall<Director>() {
			@Override
			protected void callService(AsyncCallback<Director> cb) {
				persistentService.updateDirector(director, cb);
			}

			public void onSuccess(Director result) {
				GWT.log("DirectorEditPresenter: Firing DirectorUpdateEvent");
				eventBus.fireEvent(new DirectorUpdatedEvent(result));
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving director...");
			}

		}.retry(3);
	}

}
