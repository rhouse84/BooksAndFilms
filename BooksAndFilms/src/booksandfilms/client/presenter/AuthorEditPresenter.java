package booksandfilms.client.presenter;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Author;
import booksandfilms.client.event.AuthorEditCancelledEvent;
import booksandfilms.client.event.AuthorUpdatedEvent;
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

public class AuthorEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getName();
		HasClickHandlers getSaveButton();
		Widget asWidget();
	}
	
	private Author author;
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public AuthorEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.author = new Author();
		bind();
	}
	
	public AuthorEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Long id) {
		this(queryService, persistentService, eventBus, display);
	
		if (id == null)
			return;
	      
		new RPCCall<Author>() {
			@Override
			protected void callService(AsyncCallback<Author> cb) {
				queryService.getAuthorById(id, cb);
			}

			@Override
			public void onSuccess(Author result) {
				author = result;
				AuthorEditPresenter.this.display.getName().setValue(author.getName());
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
				if (display.getName().getValue().trim().equals(author.getName())) {
					eventBus.fireEvent(new AuthorEditCancelledEvent());
				} else {
					doSave();
				}
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AuthorEditCancelledEvent());
			}
		});
		    
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void doSave() {
		author.setName(display.getName().getValue().trim());

		new RPCCall<Author>() {
			@Override
			protected void callService(AsyncCallback<Author> cb) {
				persistentService.updateAuthor(author, cb);
			}

			public void onSuccess(Author result) {
				GWT.log("AuthorEditPresenter: Firing AuthorUpdateEvent author = "+result.getName());
				eventBus.fireEvent(new AuthorUpdatedEvent(result));
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving author...");
			}

		}.retry(3);
	}

}
