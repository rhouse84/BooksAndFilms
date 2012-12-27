package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Map;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Media;
import booksandfilms.client.event.SaveBookMapEvent;
import booksandfilms.client.helper.RPCCall;

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

public class MediaGetPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getSearchButton();
		void show(int[] topLeft);
		Widget asWidget();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		void setSearchResults(ArrayList<Media> searchResults);
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private Display display;
	private String mediaName;
	
	public MediaGetPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display display, String mediaName) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		this.mediaName = mediaName;
		bind();
	}

	public void bind() {
		this.display.getSearchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//eventBus.fireEvent(new BookEditEvent(book.getId()));
				displayMessage();
			}
		});
		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					//displayMessage();
					fetchData(display.getSearchValue(), mediaName);
				}
			}
		});

	}
	
	private void fetchData(final String searchString, final String mediaName) {
		new RPCCall<ArrayList<Media>>() {
			@Override protected void callService(AsyncCallback<ArrayList<Media>> cb) {
				rpcService.getMediaData(searchString, mediaName, cb);
			}
			
			public void onSuccess(ArrayList<Media> result) {
				MediaGetPresenter.this.display.setSearchResults(result);
				//displayResults(result);
				//Got the records, do something
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching book summaries: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void displayMessage() {
		Window.alert("search was invoked "+display.getSearchValue());
	}
	
	private void displayResults(ArrayList<Media> result) {
		Window.alert("number of records = "+result.size());
	}
	
	public void go() {
	}

	@Override
	public void go(HasWidgets container) {
	}


}
