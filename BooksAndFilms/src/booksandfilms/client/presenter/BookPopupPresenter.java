package booksandfilms.client.presenter;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.entities.Book;
import booksandfilms.client.event.BookDeleteEvent;
import booksandfilms.client.event.BookEditEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class BookPopupPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getEditButton();
		HasClickHandlers getDeleteButton();
		HasText getBookTitleLabel();
		void hide();
		void setTitle(String displayName);
		void setNameAndShow(String displayName, ClickPoint location);
		Widget asWidget();
	}
	
	private Book book;
	
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private Display display;
	
	public BookPopupPresenter(PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, Book book) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.book = book;
		bind();
	}
	
	public void bind() {
		this.display.getEditButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new BookEditEvent(book.getId()));
			}
		});

		this.display.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				if (Window.confirm("Are you sure?")) {
					deleteBook(book);
				}
			}
		});

	}
	
	private void deleteBook(final Book book) {
		    
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				persistentService.deleteBook(book, cb);
			}

			@Override
			public void onSuccess(Void result) {
				eventBus.fireEvent(new BookDeleteEvent(book));
			}

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof booksandfilms.shared.exception.NotLoggedInException) {
					Window.alert("You need to login first");
				} else if (caught instanceof booksandfilms.shared.exception.CannotDeleteException) {
					Window.alert("Cannot delete a Book that has Quotes");
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
