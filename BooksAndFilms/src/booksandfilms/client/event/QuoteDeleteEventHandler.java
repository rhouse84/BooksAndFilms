package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface QuoteDeleteEventHandler extends EventHandler {
	void onDeleteQuote(QuoteDeleteEvent event);
}
