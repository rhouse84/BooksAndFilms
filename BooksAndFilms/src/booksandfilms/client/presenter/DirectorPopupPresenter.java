package booksandfilms.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import booksandfilms.client.PersistentServiceAsync;
import booksandfilms.client.entities.Director;
import booksandfilms.client.event.DirectorDeleteEvent;
import booksandfilms.client.event.DirectorEditEvent;
import booksandfilms.client.helper.ClickPoint;
import booksandfilms.client.helper.RPCCall;

public class DirectorPopupPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getEditButton();
		HasText getDirectorNameLabel();
		void hide();
		void setName(String displayName);
		void setNameAndShow(String displayName, ClickPoint location);
		Widget asWidget();
	}
	
	private Director director;
	//private DirectorPopupView popup;
	
	private final PersistentServiceAsync persistentService;
	private final SimpleEventBus eventBus;
	private Display display;
	
	public DirectorPopupPresenter(PersistentServiceAsync persistentService, SimpleEventBus eventBus, Display display, Director director) {
		this.persistentService = persistentService;
		this.eventBus = eventBus;
		this.display = display;
		this.director = director;
		bind();
	}
	
	public void bind() {
		this.display.getEditButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new DirectorEditEvent(director.getId()));
			}
		});

	}
	
	public void go() {
	}

	@Override
	public void go(HasWidgets container) {
	}

}
