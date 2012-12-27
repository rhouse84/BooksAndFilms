package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface QuoteAddEventHandler extends EventHandler {
	void onAddQuote(QuoteAddEvent event);
}
