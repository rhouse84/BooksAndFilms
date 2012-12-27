package booksandfilms.client.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.QueryServiceAsync;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.entities.Media;
import booksandfilms.client.event.DirectorAddEvent;
import booksandfilms.client.event.DirectorUpdatedEvent;
import booksandfilms.client.event.DirectorUpdatedEventHandler;
import booksandfilms.client.event.FilmAddEvent;
import booksandfilms.client.event.FilmDeleteEvent;
import booksandfilms.client.event.FilmDeleteEventHandler;
import booksandfilms.client.event.SaveFilmMapEvent;
import booksandfilms.client.event.SetScrollHeightEvent;
import booksandfilms.client.event.SetScrollHeightEventHandler;
import booksandfilms.client.event.ShowDirectorPopupEvent;
import booksandfilms.client.event.ShowFilmPopupEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
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

public class FilmListPresenter implements Presenter {

	private Map<Long, Media> filmsMap;
	private Map<Long, Media> directorsMap;
	private ArrayList<Film> films;
	private ArrayList<Director> directors;
	private ArrayList<Film> someFilms = new ArrayList<Film>();
	private ArrayList<Film> filmsByDirector = new ArrayList<Film>();
	private Director currentDirector = null;
	private Long filterId = null;
	private int scrollHeight;
	
	public interface Display {
		HasClickHandlers getAddButton();
		HasClickHandlers getList();
		void setData(List<Film> films);
		void setDirectors(List<Director> directors);
		HasChangeHandlers getDirectorFilter();
		String getDirectorValue();
		void showAddFilm();
		void hideAddFilm();
		HasKeyPressHandlers getSearchBox();
		String getSearchValue();
		int[] getClickedRow(ClickEvent event);
		ClickPoint getClickedPoint(ClickEvent event);
		boolean getAdmin();
		Widget asWidget();
		HasClickHandlers getSortTitleButton();
		HasClickHandlers getSortDirectorButton();
		HasClickHandlers getSortRatingButton();
		HasClickHandlers getSortYearButton();
		HasClickHandlers getSortWatchDateButton();
		void setScrollHeight(int parentHeight);
	}
	
	private final QueryServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;
	protected List<Director> allDirectors;
	protected List<Director> someDirectors;
	//This value is also mentioned in FilmListView
	private static final int directorColumnNumber = 2;

	public FilmListPresenter(QueryServiceAsync rpcService, SimpleEventBus eventBus, Display view, Map<Long, Media> filmsMap, Map<Long, Media> directorsMap, int scrollHeight) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
		this.filmsMap = filmsMap;
		this.directorsMap = directorsMap;
		this.scrollHeight = scrollHeight;
		bind();
	}
	
	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new FilmAddEvent(currentDirector));
			}
		});
		display.getSortTitleButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Film> cp = Film.getComparator(Film.SortParameter.TITLE_ASCENDING);
				Collections.sort(films, cp);
				display.setData(films);
			}
		});
		display.getSortDirectorButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Film> cp = Film.getComparator(Film.SortParameter.DIRECTOR_ASCENDING,Film.SortParameter.TITLE_ASCENDING);
				Collections.sort(films, cp);
				display.setData(films);
			}
		});
		display.getSortRatingButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Film> cp = Film.getComparator(Film.SortParameter.RATING_DESCENDING,Film.SortParameter.TITLE_ASCENDING);
				Collections.sort(films, cp);
				display.setData(films);
			}
		});
		display.getSortYearButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Film> cp = Film.getComparator(Film.SortParameter.YEAR_DESCENDING,Film.SortParameter.RATING_DESCENDING,Film.SortParameter.TITLE_ASCENDING);
				Collections.sort(films, cp);
				display.setData(films);
			}
		});
		display.getSortWatchDateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Comparator<Film> cp = Film.getComparator(Film.SortParameter.WATCH_DESCENDING,Film.SortParameter.TITLE_ASCENDING);
				Collections.sort(films, cp);
				display.setData(films);
			}
		});
		display.getSearchBox().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					searchBoxChange();
				}
			}
		});
		display.getDirectorFilter().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				directorChange();
			}
		});
		
		
		if (display.getList() != null)
			display.getList().addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int[] rowValues = new int[2];
					rowValues = display.getClickedRow(event);
					int selectedPropertyButtonRow = rowValues[0];
					if (selectedPropertyButtonRow >= 0) {
						int columnNumber = rowValues[1];
			            ClickPoint point = display.getClickedPoint(event);
			            Film film = someFilms.get(selectedPropertyButtonRow);
						if (columnNumber == directorColumnNumber) {
							Director director = (Director) directorsMap.get(film.getDirectorId());
				            eventBus.fireEvent(new ShowDirectorPopupEvent(director, point));
						} else {
							eventBus.fireEvent(new ShowFilmPopupEvent(film, point, allDirectors, someDirectors));
						}
					}
				}
			});

		// Listen to events

		eventBus.addHandler(FilmDeleteEvent.TYPE, new FilmDeleteEventHandler() {
			@Override public void onDeleteFilm(FilmDeleteEvent event) {
				filmsMap.remove(event.getFilm().getId());
				films = sortResults(filmsMap);
				filterFilms();
			}
		});

		eventBus.addHandler(DirectorUpdatedEvent.TYPE, new DirectorUpdatedEventHandler() {
			public void onUpdateDirector(DirectorUpdatedEvent event) {
				display.setDirectors(directors);
			}
		});

		eventBus.addHandler(SetScrollHeightEvent.TYPE, new SetScrollHeightEventHandler() {
			public void onSetHeight(SetScrollHeightEvent event) {
				display.setScrollHeight(event.getScrollHeight());
			}
		});

	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		if (filmsMap == null) {
			Log.debug("Films Map is null, therefore hit the Datastore");
			fetchFilmData();
		} else {
			Log.debug("Films Map has value, re-paint the books array.");
			films = sortResults(filmsMap);
			directors = sortDirectors(directorsMap);
			filterFilms();
			display.setDirectors(directors);
			display.hideAddFilm();
			display.setScrollHeight(scrollHeight);
		}
	}

	private void fetchFilmData() {
		new RPCCall<ArrayList<Map<Long, Media>>>() {
			@Override protected void callService(AsyncCallback<ArrayList<Map<Long, Media>>> cb) {
				rpcService.getFilmData(cb);
			}
			
			public void onSuccess(ArrayList<Map<Long, Media>> result) {
				filmsMap = result.get(0);
				films = sortResults(filmsMap);
				directorsMap = result.get(1);
				directors = sortDirectors(directorsMap);
				eventBus.fireEvent(new SaveFilmMapEvent(filmsMap, directorsMap));
				filterFilms();
				display.setDirectors(directors);
				display.hideAddFilm();
				display.setScrollHeight(BooksAndFilms.get().getMainPanel().getOffsetHeight());
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching book summaries: " + caught.getMessage());
			}
		}.retry(3);
	}
	
	private void filterFilms() {
		filmsByDirector.clear();
		someFilms.clear();
		if (filterId == null) {
			if (films != null) {
				for (Film f : films) {
					filmsByDirector.add(f);
					someFilms.add(f);
				}
			}
		} else {
			for (Film f : films) {
				if (f.getDirectorId().equals(filterId)) {
					try {
						filmsByDirector.add(f);
						someFilms.add(f);
					} catch (Exception e) {
			        	System.out.println("ERROR: could not add film to list");
						e.printStackTrace();
					}
				}
			}
		}
		display.setData(filmsByDirector);
	}

	private void searchBoxChange() {
		List<Film> tempFilms = new ArrayList<Film>();
		tempFilms = filmsByDirector;
		String searchString = display.getSearchValue();
		someFilms.clear();
		if (searchString.isEmpty()) {
			display.setData(tempFilms);
			for (Film b : tempFilms) {
				someFilms.add(b);
			}
		} else {
			for (Film b : tempFilms) {
				
				if (isYear(searchString)) {
					if (b.getYear() == Integer.parseInt(searchString)) {
						someFilms.add(b);
					}
				} else {
					if (b.getTitle().toUpperCase().startsWith(searchString.toUpperCase())) {
						someFilms.add(b);
					}
				}
			}
			display.setData(someFilms);
		}
	}
	
	private static boolean isYear(String str) {
		try	{
			double d = Double.parseDouble(str);
			if (d > 1900 && d < 2100) {
				return true;
			}
		} catch(NumberFormatException nfe) {
			return false;
		}
		return false;
	}
	
	private void directorChange() {
		if (display.getDirectorValue()== null) {
			Log.debug("Director is null, select all");
			display.hideAddFilm();
			filterId = null;
			filterFilms();
		} else if (display.getDirectorValue().equalsIgnoreCase("add new...")) {
			Log.debug("Add a new director");
			eventBus.fireEvent(new DirectorAddEvent());
		} else {
			filterId = Long.valueOf(display.getDirectorValue());
			display.showAddFilm();
			currentDirector = (Director) directorsMap.get(filterId);
			Log.debug("Director changed value = "+display.getDirectorValue());
			filterFilms();
		}
	}
	
	private ArrayList<Film> sortResults(Map<Long, Media> unSorted) {
		ArrayList<Film> sorted = new ArrayList<Film>();
		@SuppressWarnings("rawtypes")
		Iterator entries = unSorted.entrySet().iterator();
		while (entries.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry thisEntry = (Map.Entry) entries.next();
			Film film = (Film) thisEntry.getValue();
			sorted.add(film);
		}
		Comparator<Film> cp = Film.getComparator(Film.SortParameter.WATCH_DESCENDING, Film.SortParameter.RATING_DESCENDING);
		Collections.sort(sorted, cp);
		
		return sorted;
	}
	
	private ArrayList<Director> sortDirectors(Map<Long, Media> unSorted) {
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
