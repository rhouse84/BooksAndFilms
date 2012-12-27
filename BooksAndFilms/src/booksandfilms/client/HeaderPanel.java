package booksandfilms.client;

import java.util.ArrayList;

import booksandfilms.client.helper.RPCCall;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderPanel extends Composite {
	@UiField Anchor loadAuthors; 
	@UiField Anchor loadBooks; 
	@UiField Anchor loadDirectors; 
	@UiField Anchor loadFilms; 
	@UiField Anchor loadQuotes;
	@UiField VerticalPanel userBadgePanel;

	private static HeaderPanelUiBinder uiBinder = GWT.create(HeaderPanelUiBinder.class);

	interface HeaderPanelUiBinder extends UiBinder<Widget, HeaderPanel> {}
	private final QueryServiceAsync queryService = GWT.create(QueryService.class);
	private int nbrAuthors = 1;
	private int nbrBooks = 1;
	private int nbrDirectors = 1;
	private int nbrFilms = 1;
	private int nbrQuotes = 1;

	public HeaderPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		//getNumbers();
		hideLinks();
    }

	public VerticalPanel getuserBadgePanel() {
		return userBadgePanel;
	}
	
	@UiHandler("loadAuthors")
	void onAuthorClicked(ClickEvent event) {
		ProcessXML.loadAuthors();
		loadAuthors.setVisible(false);
	}

	@UiHandler("loadBooks")
	void onBookClicked(ClickEvent event) {
		ProcessXML.loadBooks();
		loadBooks.setVisible(false);
	}

	@UiHandler("loadDirectors")
	void onDirectorClicked(ClickEvent event) {
		ProcessXML.loadDirectors();
		loadDirectors.setVisible(false);
	}

	@UiHandler("loadFilms")
	void onFilmClicked(ClickEvent event) {
		ProcessXML.loadFilms();
		loadFilms.setVisible(false);
	}

	@UiHandler("loadQuotes")
	void onQuoteClicked(ClickEvent event) {
		ProcessXML.loadQuotes();
		loadQuotes.setVisible(false);
	}

//	private void getNumbers() {
//		new RPCCall<ArrayList<Integer>>() {
//			@Override protected void callService(AsyncCallback<ArrayList<Integer>> cb) {
//				queryService.getNumbers(cb);
//			}
//			@Override public void onFailure(Throwable caught) {
//				Window.alert("Error: " + caught.getMessage());
//    		}
//
//    		@Override public void onSuccess(ArrayList<Integer> numbers) {
//    			if (numbers == null) {
//    				GWT.log("This is bad");
//    				// this is bad
//    			} else {
//    				nbrAuthors = numbers.get(0);
//    				nbrBooks = numbers.get(1);
//    				nbrDirectors = numbers.get(2);
//    				nbrFilms = numbers.get(3);
//    				nbrQuotes = numbers.get(4);
//    				hideLinks();
//    			}
//    		}
//    	}.retry(3);
//
//	}
	
	private void hideLinks() {
		if (nbrAuthors > 0) {
			loadAuthors.setVisible(false);
		}
		if (nbrBooks > 0) {
			loadBooks.setVisible(false);
		}
		if (nbrDirectors > 0) {
			loadDirectors.setVisible(false);
		}
		if (nbrFilms > 0) {
			loadFilms.setVisible(false);
		}
		if (nbrQuotes > 0) {
			loadQuotes.setVisible(false);
		}
	}
}
