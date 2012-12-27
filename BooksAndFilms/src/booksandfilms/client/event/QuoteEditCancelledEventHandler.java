package booksandfilms.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface QuoteEditCancelledEventHandler extends EventHandler {
	void onEditCancelQuote(QuoteEditCancelledEvent event);
}
