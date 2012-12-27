package booksandfilms.client;


import java.util.ArrayList;
import java.util.Map;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Director;
import booksandfilms.client.entities.Film;
import booksandfilms.client.helper.RPCCall;

import com.google.gwt.xml.client.Document;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Node;

public class ProcessXML {

    // Instantiate the interfaces to access methods in the interface
    private final static PersistentServiceAsync persistentService = GWT.create(PersistentService.class);
	private static QueryServiceAsync queryService = GWT.create(QueryService.class);
	private static Map<Long, Author> authorsMap;
	private static Map<Long, Director> directorsMap;
	private static Map<Long, Book> booksMap;
	private static Map<Long, Film> filmsMap;

    public static void loadAuthors() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,"authors.xml");
        try {
        	requestBuilder.sendRequest(null, new RequestCallback() {
        		public void onError(Request request, Throwable exception) {
        			requestFailed(exception);
        		}
        		public void onResponseReceived(Request request, Response response) {
        			GWT.log("Rendering the Author XML");
        			renderAuthorXML(response.getText());
        		}
        	});
        } catch (RequestException ex) {
        	requestFailed(ex);
        }
	}

    public static void loadDirectors() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,"directors.xml");
        try {
        	requestBuilder.sendRequest(null, new RequestCallback() {
        		public void onError(Request request, Throwable exception) {
        			requestFailed(exception);
        		}
        		public void onResponseReceived(Request request, Response response) {
        			renderDirectorXML(response.getText());
        		}
        	});
        } catch (RequestException ex) {
        	requestFailed(ex);
        }
	}

    public static void loadBooks() {

		new RPCCall<Map<Long, Author>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Author>> cb) {
				queryService.getAllAuthors(cb);
			}
			
			@Override public void onSuccess(Map<Long, Author> result) {
				authorsMap = result;
				processBooks();
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching author summaries: " + caught.getMessage());
			}
		}.retry(3);

    }

    private static void processBooks() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,"books.xml");
        try {
        	requestBuilder.sendRequest(null, new RequestCallback() {
        		public void onError(Request request, Throwable exception) {
        			requestFailed(exception);
        		}
        		public void onResponseReceived(Request request, Response response) {
        			renderBookXML(response.getText());
        		}
        	});
        } catch (RequestException ex) {
        	requestFailed(ex);
        }
    }
    
    public static void loadFilms() {

		new RPCCall<Map<Long, Director>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Director>> cb) {
				queryService.getAllDirectors(cb);
			}
			
			@Override public void onSuccess(Map<Long, Director> result) {
				directorsMap = result;
				processFilms();
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching director summaries: " + caught.getMessage());
			}
		}.retry(3);

    }

    public static void processFilms() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,"films.xml");
        try {
        	requestBuilder.sendRequest(null, new RequestCallback() {
        		public void onError(Request request, Throwable exception) {
        			requestFailed(exception);
        		}
        		public void onResponseReceived(Request request, Response response) {
        			renderFilmXML(response.getText());
        		}
        	});
        } catch (RequestException ex) {
        	requestFailed(ex);
        }
	}

    public static void loadQuotes() {

		new RPCCall<Map<Long, Book>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Book>> cb) {
				//queryService.getBooks(cb);
			}
			
			@Override public void onSuccess(Map<Long, Book> result) {
				booksMap = result;
				loadQuotes1();
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching director summaries: " + caught.getMessage());
			}
		}.retry(3);

    }

    public static void loadQuotes1() {

		new RPCCall<Map<Long, Film>>() {
			@Override protected void callService(AsyncCallback<Map<Long, Film>> cb) {
				//queryService.getFilms(cb);
			}
			
			@Override public void onSuccess(Map<Long, Film> result) {
				filmsMap = result;
				processQuotes();
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error fetching director summaries: " + caught.getMessage());
			}
		}.retry(3);

    }

    public static void processQuotes() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,"quotes.xml");
        try {
        	requestBuilder.sendRequest(null, new RequestCallback() {
        		public void onError(Request request, Throwable exception) {
        			requestFailed(exception);
        		}
        		public void onResponseReceived(Request request, Response response) {
        			renderQuoteXML(response.getText());
        		}
        	});
        } catch (RequestException ex) {
        	requestFailed(ex);
        }
	}

	private static void renderAuthorXML(String responseText) {
	    Document authorDom = XMLParser.parse(responseText);
	    Element authorsElement = authorDom.getDocumentElement();
	    NodeList listOfAuthors = authorsElement.getElementsByTagName("row");
	    int totalAuthors = listOfAuthors.getLength();
	    System.out.println("Total no of authors : " + totalAuthors);
	    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	    for(int s=0; s<listOfAuthors.getLength() ; s++){
	    
	    	Node firstAuthorNode = listOfAuthors.item(s);
	    	if(firstAuthorNode.getNodeType() == Node.ELEMENT_NODE){
	    		Element authorElement = (Element) firstAuthorNode;
	    		String authorIdValue = getElementTextValue(authorElement, "author_id");
	    		String nameValue = getElementTextValue(authorElement, "name");
		    	ArrayList<String> authRec = new ArrayList<String>();
	    		data.add( authRec );
	    		authRec.add(authorIdValue);
	    		authRec.add(nameValue);
	    		
	    	} // end of if clause
	    } // end of for
	    
        persistentService.persistAuthorList(data, 
                new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        System.out.println("PersistentService RPC call failed " + caught);
                    }
                    @Override
                    public void onSuccess(Void result) {
                        System.out.println("PersistentService RPC call succedded");
                    }
                });
        
	} // end of method

	private static void renderDirectorXML(String responseText) {
	    Document directorDom = XMLParser.parse(responseText);
	    Element directorsElement = directorDom.getDocumentElement();
	    NodeList listOfDirectors = directorsElement.getElementsByTagName("row");
	    int totalDirectors = listOfDirectors.getLength();
	    System.out.println("Total no of directors : " + totalDirectors);
	    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	    for(int s=0; s<listOfDirectors.getLength() ; s++){
	    
	    	Node firstDirectorNode = listOfDirectors.item(s);
	    	if(firstDirectorNode.getNodeType() == Node.ELEMENT_NODE){
	    		Element directorElement = (Element) firstDirectorNode;
	    		String directorIdValue = getElementTextValue(directorElement, "director_id");
	    		String nameValue = getElementTextValue(directorElement, "name");
		    	ArrayList<String> dirRec = new ArrayList<String>();
	    		data.add( dirRec );
	    		dirRec.add(directorIdValue);
	    		dirRec.add(nameValue);
	    		
	    	} // end of if clause
	    } // end of for

	    persistentService.persistDirectorList(data, 
                new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        System.out.println("PersistentService RPC call failed " + caught);
                    }
                    @Override
                    public void onSuccess(Void result) {
                        System.out.println("PersistentService RPC call succedded");
                    }
                });

	} // end of method

	private static void renderBookXML(String responseText) {
		Document bookDom = XMLParser.parse(responseText);
	    Element booksElement = bookDom.getDocumentElement();
	    NodeList listOfBooks = booksElement.getElementsByTagName("book");
	    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	    for(int s=0; s<listOfBooks.getLength() ; s++){
			    
	    	Node firstBookNode = listOfBooks.item(s);
	    	if(firstBookNode.getNodeType() == Node.ELEMENT_NODE){
	    		Element bookElement = (Element) firstBookNode;
	    		String idValue = getElementTextValue(bookElement, "id");
	    		String titleValue = getElementTextValue(bookElement, "title");
	    		String authorIdValue = getElementTextValue(bookElement, "author_id");
	    		String genreValue = getElementTextValue(bookElement, "genre");
	    		String ratingValue = getElementTextValue(bookElement, "rating");
	    		String notesValue = getElementTextValue(bookElement, "notes");
	    		String createDateValue = getElementTextValue(bookElement, "create_date");
	    		String readDateValue = getElementTextValue(bookElement, "read_date");
		    	ArrayList<String> bookRec = new ArrayList<String>();
	    		data.add( bookRec );
	    		bookRec.add(idValue);
	    		bookRec.add(titleValue);
	    		bookRec.add(authorIdValue);
	    		bookRec.add(genreValue);
	    		bookRec.add(ratingValue);
	    		bookRec.add(notesValue);
	    		bookRec.add(createDateValue);
	    		bookRec.add(readDateValue);
	    		Author author = authorsMap.get(Long.parseLong(authorIdValue));
	    		bookRec.add(author.getName());
		    		
	    	} // end of if clause
	    } // end of for
	    	
    	if (data.size() > 0) {
    		persistentService.persistBookList(data, 
    			new AsyncCallback<Void>() {
                   	@Override
                   	public void onFailure(Throwable caught) {
                   		System.out.println("PersistentService RPC call failed " + caught);
                   	}
                   	@Override
                   	public void onSuccess(Void result) {
                   		System.out.println("PersistentService RPC call succedded");
                   	}
               	});
    	}

	} // end of method

	private static void renderFilmXML(String responseText) {
		//TODO: Must load the directors in a Map.
	    Document filmDom = XMLParser.parse(responseText);
	    Element filmsElement = filmDom.getDocumentElement();
	    NodeList listOfFilms = filmsElement.getElementsByTagName("film");
	    int totalFilms = listOfFilms.getLength();
	    System.out.println("Total no of films : " + totalFilms);
	    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	    for(int s=0; s<listOfFilms.getLength() ; s++){
	    
	    	Node firstFilmNode = listOfFilms.item(s);
	    	if(firstFilmNode.getNodeType() == Node.ELEMENT_NODE){
	    		Element filmElement = (Element) firstFilmNode;
	    		String idValue = getElementTextValue(filmElement, "id");
	    		String titleValue = getElementTextValue(filmElement, "title");
	    		String directorIdValue = getElementTextValue(filmElement, "director_id");
	    		String yearValue = getElementTextValue(filmElement, "year");
	    		String ratingValue = getElementTextValue(filmElement, "rating");
	    		String notesValue = getElementTextValue(filmElement, "notes");
	    		String starsValue = getElementTextValue(filmElement, "stars");
	    		String createDateValue = getElementTextValue(filmElement, "create_date");
	    		String watchDateValue = getElementTextValue(filmElement, "watch_date");
		    	ArrayList<String> filmRec = new ArrayList<String>();
	    		data.add( filmRec );
	    		filmRec.add(idValue);
	    		filmRec.add(titleValue);
	    		filmRec.add(directorIdValue);
	    		filmRec.add(yearValue);
	    		filmRec.add(ratingValue);
	    		filmRec.add(notesValue);
	    		filmRec.add(starsValue);
	    		filmRec.add(createDateValue);
	    		filmRec.add(watchDateValue);
	    		Director director = directorsMap.get(Long.parseLong(directorIdValue));
	    		filmRec.add(director.getName());
	    		
	    	} // end of if clause
	    } // end of for

	    persistentService.persistFilmList(data, 
                new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        System.out.println("PersistentService RPC call failed " + caught);
                    }
                    @Override
                    public void onSuccess(Void result) {
                        System.out.println("PersistentService RPC call succedded");
                    }
                });

	} // end of method

	private static void renderQuoteXML(String responseText) {
		//TODO: Must load the books and films in a Map for each.
	    Document quoteDom = XMLParser.parse(responseText);
	    Element quotesElement = quoteDom.getDocumentElement();
	    NodeList listOfQuotes = quotesElement.getElementsByTagName("row");
	    int totalQuotes = listOfQuotes.getLength();
	    System.out.println("Total no of quotes : " + totalQuotes);
	    ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	    for(int s=0; s<listOfQuotes.getLength() ; s++){
	    
	    	Node firstQuoteNode = listOfQuotes.item(s);
	    	if(firstQuoteNode.getNodeType() == Node.ELEMENT_NODE){
	    		Element quoteElement = (Element) firstQuoteNode;
	    		String quoteIdValue = getElementTextValue(quoteElement, "id");
	    		String bookIdValue = getElementTextValue(quoteElement, "book_id");
	    		String filmIdValue = getElementTextValue(quoteElement, "film_id");
	    		String characterNameValue = getElementTextValue(quoteElement, "character_name");
	    		String quoteTextValue = getElementTextValue(quoteElement, "quote");
		    	ArrayList<String> quoteRec = new ArrayList<String>();
	    		data.add( quoteRec );
	    		quoteRec.add(quoteIdValue);
	    		quoteRec.add(bookIdValue);
	    		quoteRec.add(filmIdValue);
	    		quoteRec.add(characterNameValue);
	    		quoteRec.add(quoteTextValue);
	    		if (Long.parseLong(bookIdValue) > 0) {
	    			Book book = booksMap.get(Long.parseLong(bookIdValue));
	    			quoteRec.add(book.getTitle());
	    		} else {
	    			quoteRec.add("NULL");
	    		}
	    		if (Long.parseLong(filmIdValue) > 0) {
	    			Film film = filmsMap.get(Long.parseLong(filmIdValue));
	    			quoteRec.add(film.getTitle());
	    		} else {
	    			quoteRec.add("NULL");
	    		}
	    		
	    	} // end of if clause
	    } // end of for

	    persistentService.persistQuoteList(data,
	    		new AsyncCallback<Void>() {
            	@Override
            	public void onFailure(Throwable caught) {
            		System.out.println("PersistentService RPC call failed " + caught);
            	}
            	@Override
            	public void onSuccess(Void result) {
            		System.out.println("PersistentService RPC call succedded");
            	}
	    });

	} // end of method

	private static String getElementTextValue(Element parent, String elementTag) {
		try {
			return parent.getElementsByTagName(elementTag).item(0).getFirstChild().getNodeValue();
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	private static void requestFailed(Throwable exception) {
		Window.alert("Request failed: " + exception.getMessage());
	}

}
