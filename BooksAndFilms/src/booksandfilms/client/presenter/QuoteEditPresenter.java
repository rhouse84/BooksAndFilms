package booksandfilms.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Quote;
import booksandfilms.client.event.QuoteEditCancelledEvent;
import booksandfilms.client.event.QuoteUpdatedEvent;
import booksandfilms.client.helper.RPCCall;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class QuoteEditPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getCancelButton();
		HasValue<String> getName();
		HasValue<String> getQuote();
		HasClickHandlers getSaveButton();
		Widget asWidget();
	}
	
	private Quote quote;
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private final Display display;
	private final Object type;

	public QuoteEditPresenter(QueryServiceAsync queryService, PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, Object type) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.quote = new Quote();
		this.type = type;
		bind();
	}
	
	public QuoteEditPresenter(final QueryServiceAsync queryService, final PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, final Long id, Object type) {
		this(queryService, persistentService, eventBus, display, type);
	
		if (id == null)
			return;
	      
		new RPCCall<Quote>() {
			@Override
			protected void callService(AsyncCallback<Quote> cb) {
				queryService.getQuoteById(id, cb);
			}

			@Override
			public void onSuccess(Quote result) {
				quote = result;
				QuoteEditPresenter.this.display.getName().setValue(quote.getCharacterName());
				QuoteEditPresenter.this.display.getQuote().setValue(quote.getQuoteText());
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
				eventBus.fireEvent(new QuoteEditCancelledEvent());
			}
		});
		    
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void doSave() {
		quote.setQuoteText(display.getQuote().getValue().trim());
		quote.setCharacterName(display.getName().getValue().trim());
		if (type instanceof Book) {
			quote.setBookId(((Book) type).getId());
			quote.setBookTitle(((Book) type).getTitle());
			quote.setFilmId(null);
		} else if (type instanceof Film) {
			quote.setFilmId(((Film) type).getId());
			quote.setFilmTitle(((Film) type).getTitle());
			quote.setBookId(null);
		}
			

		new RPCCall<Quote>() {
			@Override
			protected void callService(AsyncCallback<Quote> cb) {
				persistentService.updateQuote(quote, cb);
			}

			public void onSuccess(Quote result) {
				eventBus.fireEvent(new QuoteUpdatedEvent(result));
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving quote...");
			}

		}.retry(3);
	}

}
