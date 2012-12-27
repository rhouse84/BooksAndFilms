package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface QuoteUpdatedEventHandler extends EventHandler {
	void onUpdateQuote(QuoteUpdatedEvent event);
}
