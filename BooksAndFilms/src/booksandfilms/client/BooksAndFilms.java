package booksandfilms.client;

import java.util.Map;

import booksandfilms.client.Resources.GlobalResources;
import booksandfilms.client.entities.Media;
import booksandfilms.client.entities.UserAccount;
import booksandfilms.client.event.LoginEvent;
import booksandfilms.client.event.SetScrollHeightEvent;
import booksandfilms.client.view.FilmListView;
import booksandfilms.client.view.LoginView;
import booksandfilms.client.view.QuoteAllView;
import booksandfilms.client.view.UserBadgeView;
import booksandfilms.client.helper.RPCCall;
import booksandfilms.client.presenter.FilmListPresenter;
import booksandfilms.client.presenter.LoginPresenter;
import booksandfilms.client.presenter.QuoteAllPresenter;
import booksandfilms.client.presenter.UserBadgePresenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.allen_sauer.gwt.log.client.Log;

public class BooksAndFilms implements EntryPoint {

	interface BooksAndFilmsUiBinder extends UiBinder<DockLayoutPanel, BooksAndFilms> {}

	/**
	 * This field gets compiled out when <code>log_level=OFF</code>, or any <code>log_level</code>
	 * higher than <code>DEBUG</code>.
	 */
	private long startTimeMillis;

	/**
	 * Note, we defer all application initialization code to {@link #onModuleLoad2()} so that the
	 * UncaughtExceptionHandler can catch any unexpected exceptions.
	 */

	@UiField HeaderPanel headerPanel;
	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel mainFilmPanel;
	@UiField HTMLPanel mainQuotePanel;
	//@UiField HTMLPanel otherPanel;
	RootLayoutPanel root;
	
	private static BooksAndFilms singleton;
	private UserAccount currentUser;
	private SimpleEventBus eventBus = new SimpleEventBus();
	private Map<Long, Media> directorsMap = null;
	private Map<Long, Media> filmsMap = null;
	private Map<Long, Media> quotesMap = null;
	private int scrollHeight = 0;
	
	// Presenters
	private FilmListPresenter filmListPresenter;
	private UserBadgePresenter userBadgePresenter;
	private QuoteAllPresenter quoteAllPresenter;
//	private TopicTestPresenter topicTestPresenter;
	
	// RPC services
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private QueryServiceAsync queryService = GWT.create(QueryService.class);
	private PersistentServiceAsync persistentService = GWT.create(PersistentService.class);
	

	public static BooksAndFilms get() {
		return singleton;
	}

	private static final BooksAndFilmsUiBinder binder = GWT.create(BooksAndFilmsUiBinder.class);

	public void onModuleLoad() {

		/*
	     * Install an UncaughtExceptionHandler which will produce <code>FATAL</code> log messages
	     */
		Log.setUncaughtExceptionHandler();

	    // use deferred command to catch initialization exceptions in onModuleLoad2
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				onModuleLoad2();
			}
		});
	}

	/**
	 * Deferred initialization method, used by {@link #onModuleLoad()}.
	 */
	private void onModuleLoad2() {

		/*
	     * Use a <code>if (Log.isDebugEnabled()) {...}</code> guard to ensure that
	     * <code>System.currentTimeMillis()</code> is compiled out when <code>log_level=OFF</code>, or
	     * any <code>log_level</code> higher than <code>DEBUG</code>.
	     */
	    if (Log.isDebugEnabled()) {
	    	startTimeMillis = System.currentTimeMillis();
	    }

	    /*
	     * Again, we need a guard here, otherwise <code>log_level=OFF</code> would still produce the
	     * following useless JavaScript: <pre> var durationSeconds, endTimeMillis; endTimeMillis =
	     * currentTimeMillis_0(); durationSeconds = (endTimeMillis - this$static.startTimeMillis) /
	     * 1000.0; </pre>
	     */
	    if (Log.isDebugEnabled()) {
	      long endTimeMillis = System.currentTimeMillis();
	      float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
	      Log.debug("Duration: " + durationSeconds + " seconds");
	    }

        singleton = this;
		GlobalResources.RESOURCE.css().ensureInjected();

        getLoggedInUser();

    } //onModuleLoad
	
	private void getLoggedInUser() {
		new RPCCall<UserAccount>() {
			@Override protected void callService(AsyncCallback<UserAccount> cb) {
				loginService.getLoggedInUser(cb);
			}
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
    		}

    		@Override public void onSuccess(UserAccount currentUser) {
    			if (currentUser == null) {
    				// nobody is logged in
    				showLoginView();
    			} else {
    				setCurrentUser(currentUser);
    				createUI();
    			}
    		}
    	}.retry(3);

	}
	
	public void showLoginView() {
	    root = RootLayoutPanel.get();
	    root.clear();
	    LoginPresenter loginPresenter = new LoginPresenter(eventBus, new LoginView());
	    loginPresenter.go(root);
	}
	
	public SimpleEventBus getEventBus() {
		return eventBus;
	}

	private void createUI() {

		GWT.runAsync(new RunAsyncCallback() {
			@Override public void onFailure(Throwable reason) {
				Window.alert("Code download error: " + reason.getMessage());
	        }

	        @Override public void onSuccess() {
	        	goAfterLogin();
	        	eventBus.fireEvent(new LoginEvent(currentUser));
	        }
	      });

	}
	
	private void goAfterLogin() {
	    DockLayoutPanel outer = binder.createAndBindUi(this);

        root = RootLayoutPanel.get();
        root.clear();
        root.add(outer);
		
        AppController appViewer = new AppController(persistentService, queryService, eventBus);
        appViewer.go();
        
        //Here:
        //Get the Data, including:
        //All authors, directors and quotes. 
        //On success of above data, get Keys for Books and Films and save file in AppController
        //Inside presenter, get Book/Film data in chunks by passing group of keys, keep track of offset
        //If offset is same size as booksMap, we know we have to retrieve the next chunk.
        //If offset is less, display data from booksMap
        
        filmListPresenter = new FilmListPresenter(queryService, eventBus, new FilmListView(), filmsMap, directorsMap, scrollHeight);
        filmListPresenter.go(mainFilmPanel);

        quoteAllPresenter = new QuoteAllPresenter(queryService, eventBus, new QuoteAllView(), quotesMap, null);
        quoteAllPresenter.go(mainQuotePanel);

//        topicTestPresenter = new TopicTestPresenter(queryService, eventBus, new TopicTestView());
//        topicTestPresenter.go(otherPanel);

        userBadgePresenter = new UserBadgePresenter(loginService, eventBus, new UserBadgeView());
        userBadgePresenter.go(headerPanel.getuserBadgePanel());
        
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
		        eventBus.fireEvent(new SetScrollHeightEvent(mainPanel.getOffsetHeight()));
			}
		});

	}
	
	void setCurrentUser(UserAccount currentUser) {
		this.currentUser = currentUser;
	}
	
	public UserAccount getCurrentUser() {
		return currentUser;
	}
	
	public HTMLPanel getMainPanel() {
		return mainPanel;
	}

	public HTMLPanel getMainFilmPanel() {
		return mainFilmPanel;
	}

	public HTMLPanel getMainQuotePanel() {
		return mainQuotePanel;
	}
	
}
