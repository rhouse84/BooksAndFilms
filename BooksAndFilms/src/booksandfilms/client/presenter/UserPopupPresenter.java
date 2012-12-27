package booksandfilms.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.entities.UserAccount;
import booksandfilms.client.event.UserDeleteEvent;
import booksandfilms.client.event.UserEditEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

public class UserPopupPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getEditButton();
		HasClickHandlers getDeleteButton();
		HasText getUserNameLabel();
		void hide();
		void setName(String displayName);
		void setNameAndShow(String displayName, ClickPoint location);
		Widget asWidget();
	}
	
	private UserAccount user;
	//private UserAccountPopupView popup;
	
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private Display display;
	
	public UserPopupPresenter(PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, UserAccount user) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.user = user;
		bind();
	}
	
	public void bind() {
		this.display.getEditButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new UserEditEvent(user.getId()));
			}
		});

		this.display.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				if (Window.confirm("Are you sure?")) {
					deleteUser(user);
				}
			}
		});

	}
	
	private void deleteUser(final UserAccount user) {
		    
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				persistentService.deleteUser(user, cb);
			}

			@Override
			public void onSuccess(Void result) {
				eventBus.fireEvent(new UserDeleteEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof booksandfilms.shared.exception.NotLoggedInException) {
					Window.alert("You need to login first");
				} else if (caught instanceof booksandfilms.shared.exception.CannotDeleteException) {
					Window.alert("Cannot delete a User that has Authors, Directors or Topics");
				} else {
					Window.alert("An error occurred: " + caught.toString());
				}
			}

		}.retry(3);

	}

	public void go() {
	}

	@Override
	public void go(HasWidgets container) {
	}

}
