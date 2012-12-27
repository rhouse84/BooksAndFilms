package booksandfilms.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.entities.Author;
import booksandfilms.client.event.AuthorEditEvent;
import booksandfilms.client.helper.ClickPoint;

public class AuthorPopupPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getEditButton();
		HasText getAuthorNameLabel();
		void hide();
		void setName(String displayName);
		void setNameAndShow(String displayName, ClickPoint location);
		Widget asWidget();
	}
	
	private Author author;
	//private AuthorPopupView popup;
	
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private Display display;
	
	public AuthorPopupPresenter(PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, Author author) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.author = author;
		bind();
	}
	
	public void bind() {
		this.display.getEditButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new AuthorEditEvent(author.getId()));
			}
		});

	}
	
	public void go() {
	}

	@Override
	public void go(HasWidgets container) {
	}

}
