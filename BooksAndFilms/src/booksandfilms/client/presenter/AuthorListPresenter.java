package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
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
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Author;
import booksandfilms.client.event.AuthorAddEvent;
import booksandfilms.client.event.AuthorDeleteEvent;
import booksandfilms.client.event.AuthorDeleteEventHandler;
import booksandfilms.client.event.AuthorListChangedEvent;
import booksandfilms.client.event.AuthorUpdatedEvent;
import booksandfilms.client.event.AuthorUpdatedEventHandler;
import booksandfilms.client.event.HideAddBookEvent;
import booksandfilms.client.event.SaveAuthorMapEvent;
import booksandfilms.client.event.ShowAddBookEvent;
import booksandfilms.client.event.ShowAuthorPopupEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

public class AuthorListPresenter implements Presenter {

	private Map<Long, Author> authorsMap;
	private ArrayList<Author> authors;
	private ArrayList<Integer> selectedRows;
	private ArrayList<Author> someAuthors = new ArrayList<Author>();
	
	public interface Display {
		HasClickHandlers getAddButton();
		HasClickHandlers getList();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		void setData(ArrayList<Author> authorList);
		int getClickedRow(ClickEvent event);
		ClickPoint getClickedPoint(ClickEvent event);
		List<Integer> getSelectedRows();
		boolean getAdmin();
		Widget asWidget();
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	
	public AuthorListPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view, Map<Long, Author> authorsMap) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.authorsMap = authorsMap;
		bind();
	}
	
	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AuthorAddEvent());
			}
		});
		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					searchBoxChange();
				}
			}
		});
		
		if (display.getList() != null)
			display.getList().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int selectedPropertyButtonRow = display.getClickedRow(event);
					if (selectedPropertyButtonRow >= 0) {
			            ClickPoint point = display.getClickedPoint(event);
			            Author author = authors.get(selectedPropertyButtonRow);
			            eventBus.fireEvent(new ShowAuthorPopupEvent(author, point));
					} else {
						selectedRows = (ArrayList<Integer>) display.getSelectedRows();
						fireAuthorListChangeEvent();
					}
				}
			});
		
		// Listen to events
		eventBus.addHandler(AuthorUpdatedEvent.TYPE, new AuthorUpdatedEventHandler() {
	    	@Override public void onUpdateAuthor(AuthorUpdatedEvent event) {
	    		authorsMap.put(event.getAuthor().getId(), event.getAuthor());
				authors = sortResults(authorsMap);
				display.setData(authors);
	    	}
	    });

		eventBus.addHandler(AuthorDeleteEvent.TYPE, new AuthorDeleteEventHandler() {
			@Override public void onDeleteAuthor(AuthorDeleteEvent event) {
				authorsMap.remove(event.getAuthor().getId());
				authors = sortResults(authorsMap);
				display.setData(authors);
			}
		});

	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		if (authorsMap == null) {
			GWT.log("Authors Map is null, therefore hit the Datastore");
			fetchAuthorSummary();
		} else {
			GWT.log("Authors Map has value, re-paint the authors array.");
			authors = sortResults(authorsMap);
			display.setData(authors);
		}
		
	}

	private void fetchAuthorSummary() {
		new RPCCall<Map<Long, Author>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Author>> cb) {
				rpcService.getAllAuthors(cb);
			}
			
			@Override public void onSuccess(Map<Long, Author> result) {
				authorsMap = result;
				authors = sortResults(authorsMap); 
				display.setData(authors);
				eventBus.fireEvent(new SaveAuthorMapEvent(authorsMap));
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching author summaries: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void fireAuthorListChangeEvent() {
		Integer row;
		ArrayList<Author> selection = new ArrayList<Author>();
		if (selectedRows != null) {
			for (Iterator<Integer> i = selectedRows.iterator(); i.hasNext();) {
				row = (Integer) i.next();
				if (someAuthors.size() > 0) {
					selection.add(someAuthors.get(row));
				} else {
					selection.add(authors.get(row));
				}
			}
		}

		eventBus.fireEvent(new AuthorListChangedEvent(selection, authors));
		if (selection.size() == 1) {
			eventBus.fireEvent(new ShowAddBookEvent(selection.get(0)));
		} else {
			eventBus.fireEvent(new HideAddBookEvent());
		}
		
	}
	
	private void searchBoxChange() {
		String searchString = display.getSearchValue();
		someAuthors.clear();
		if (searchString.isEmpty()) {
			display.setData(authors);
		} else {
			for (Author a : authors) {
				if (a.getName().toUpperCase().startsWith(searchString.toUpperCase()))
					someAuthors.add(a);
			}
			display.setData(someAuthors);
		}
	}
	
	private ArrayList<Author> sortResults(Map<Long, Author> unSorted) {
		ArrayList<Author> sorted = new ArrayList<Author>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Author author = (Author) thisEntry.getValue();
			sorted.add(author);
		}
		Comparator<Author> cp = Author.getComparator(Author.SortParameter.NAME_ASCENDING);
		Collections.sort(sorted, cp);
		
		return sorted;
	}
	
}
