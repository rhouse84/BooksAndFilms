package booksandfilms.client.presenter;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.UserAccount;
import booksandfilms.client.event.UserEditCancelledEvent;
import booksandfilms.client.event.UserUpdatedEvent;
import booksandfilms.client.helper.RPCCall;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class UserEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getName();
		HasValue<String> getEmail();
		HasText getLastLogin();
		void setAdminData(Boolean admin);
		String getAdminValue();
		HasClickHandlers getSaveButton();
		Widget asWidget();
	}

	private UserAccount user;
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public UserEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.user = new UserAccount();
		bind();
	}
	
	public UserEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Long id) {
		this(queryService, persistentService, eventBus, display);
	
		if (id == null)
			return;
	      
		new RPCCall<UserAccount>() {
			@Override
			protected void callService(AsyncCallback<UserAccount> cb) {
				queryService.getUserById(id, cb);
			}

			@Override
			public void onSuccess(UserAccount result) {
				user = result;
				UserEditPresenter.this.display.getName().setValue(user.getName());
				UserEditPresenter.this.display.getEmail().setValue(user.getEmailAddress());
				UserEditPresenter.this.display.getLastLogin().setText(user.getLastLoginOn().toString());
				UserEditPresenter.this.display.setAdminData(user.getAdminUser());
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
				GWT.log("UserEditPresenter: Firing UserEditCancelledEvent");
				eventBus.fireEvent(new UserEditCancelledEvent());
			}
		});
		    
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void doSave() {
		user.setName(display.getName().getValue().trim());
		user.setEmailAddress(display.getEmail().getValue().trim());
		if (display.getAdminValue().equalsIgnoreCase("true")) {
			user.setAdminUser(true);
		} else {
			user.setAdminUser(false);
		}
		
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				persistentService.updateUser(user, cb);
			}

			public void onSuccess(Void result) {
				GWT.log("UserEditPresenter: Firing UserUpdateEvent");
				eventBus.fireEvent(new UserUpdatedEvent());
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving user...");
			}

		}.retry(3);
	}

}
