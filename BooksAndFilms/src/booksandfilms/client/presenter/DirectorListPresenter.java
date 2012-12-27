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
import booksandfilms.client.entities.Director;
import booksandfilms.client.event.DirectorAddEvent;
import booksandfilms.client.event.DirectorDeleteEvent;
import booksandfilms.client.event.DirectorDeleteEventHandler;
import booksandfilms.client.event.DirectorListChangedEvent;
import booksandfilms.client.event.DirectorUpdatedEvent;
import booksandfilms.client.event.DirectorUpdatedEventHandler;
import booksandfilms.client.event.HideAddFilmEvent;
import booksandfilms.client.event.SaveDirectorMapEvent;
import booksandfilms.client.event.ShowAddFilmEvent;
import booksandfilms.client.event.ShowDirectorPopupEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

public class DirectorListPresenter implements Presenter {

	private Map<Long, Director> directorsMap;
	private List<Director> directors;
	private List<Integer> selectedRows;
	private List<Director> someDirectors = new ArrayList<Director>();
	
	public interface Display {
		HasClickHandlers getAddButton();
		HasClickHandlers getList();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		void setData(List<Director> directorNames);
		int getClickedRow(ClickEvent event);
		ClickPoint getClickedPoint(ClickEvent event);
		List<Integer> getSelectedRows();
		boolean getAdmin();
		Widget asWidget();
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	
	public DirectorListPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view, Map<Long, Director> directorsMap) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.directorsMap = directorsMap;
		bind();
	}
	
	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new DirectorAddEvent());
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
			            Director director = directors.get(selectedPropertyButtonRow);
			            eventBus.fireEvent(new ShowDirectorPopupEvent(director, point));
					} else {
						selectedRows = display.getSelectedRows();
						fireDirectorListChangeEvent();
					}
				}
			});
		// Listen to events
		eventBus.addHandler(DirectorUpdatedEvent.TYPE, new DirectorUpdatedEventHandler() {
	    	@Override public void onUpdateDirector(DirectorUpdatedEvent event) {
	    		directorsMap.put(event.getDirector().getId(), event.getDirector());
				directors = sortResults(directorsMap);
				display.setData(directors);
	        	//fetchDirectorSummary();
	    	}
	    });

		eventBus.addHandler(DirectorDeleteEvent.TYPE, new DirectorDeleteEventHandler() {
			@Override public void onDeleteDirector(DirectorDeleteEvent event) {
				directorsMap.remove(event.getDirector().getId());
				directors = sortResults(directorsMap);
				display.setData(directors);
				fetchDirectorSummary();
			}
		});

	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		if (directorsMap == null) {
			GWT.log("Directors Map is null, therefore hit the Datastore");
			fetchDirectorSummary();
		} else {
			GWT.log("Directors Map has value, re-paint the authors array.");
			directors = sortResults(directorsMap);
			display.setData(directors);
		}
	}

	private void fetchDirectorSummary() {
		new RPCCall<Map<Long, Director>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Director>> cb) {
				rpcService.getAllDirectors(cb);
			}
			
			@Override public void onSuccess(Map<Long, Director> result) {
				directorsMap = result;
				directors = sortResults(directorsMap); 
				display.setData(directors);
				eventBus.fireEvent(new SaveDirectorMapEvent(directorsMap));
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching director summaries: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void fireDirectorListChangeEvent() {
		Integer row;
		ArrayList<Director> selection = new ArrayList<Director>();
		if (selectedRows != null) {
			for (Iterator<Integer> i = selectedRows.iterator(); i.hasNext();) {
				row = (Integer) i.next();
				if (someDirectors.size() > 0) {
					selection.add(someDirectors.get(row));
				} else {
					selection.add(directors.get(row));
				}
			}
		}

		eventBus.fireEvent(new DirectorListChangedEvent(selection, directors));
		if (selection.size() == 1) {
			eventBus.fireEvent(new ShowAddFilmEvent(selection.get(0)));
		} else {
			eventBus.fireEvent(new HideAddFilmEvent());
		}
		
	}
	
	private void searchBoxChange() {
		String searchString = display.getSearchValue();
		someDirectors.clear();
		if (searchString.isEmpty()) {
			display.setData(directors);
		} else {
			for (Director a : directors) {
				if (a.getName().toUpperCase().startsWith(searchString.toUpperCase()))
					someDirectors.add(a);
			}
			display.setData(someDirectors);
		}
	}

	private ArrayList<Director> sortResults(Map<Long, Director> unSorted) {
		ArrayList<Director> sorted = new ArrayList<Director>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Director director = (Director) thisEntry.getValue();
			sorted.add(director);
		}
		Comparator<Director> cp = Director.getComparator(Director.SortParameter.NAME_ASCENDING);
		Collections.sort(sorted, cp);
		
		return sorted;
	}
	
}
