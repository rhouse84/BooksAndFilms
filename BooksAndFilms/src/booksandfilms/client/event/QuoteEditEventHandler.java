package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface QuoteEditEventHandler extends EventHandler {
	void onEditQuote(QuoteEditEvent event);
}
