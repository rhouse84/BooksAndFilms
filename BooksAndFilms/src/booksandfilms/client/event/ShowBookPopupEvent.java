package booksandfilms.client.event;

import java.util.List;

import booksandfilms.client.entities.Author;
import booksandfilms.client.entities.Book;
import booksandfilms.client.helper.ClickPoint;

import com.google.gwt.event.shared.GwtEvent;

public class ShowBookPopupEvent extends GwtEvent<ShowBookPopupEventHandler> {
	public static Type<ShowBookPopupEventHandler> TYPE = new Type<ShowBookPopupEventHandler>();
	private final Book book;
	private final ClickPoint point;
	private List<Author> allAuthors;
	private List<Author> someAuthors;
	
	public ClickPoint getClickPoint() {
		return point;
	}
	
	public ShowBookPopupEvent(Book book, ClickPoint point, List<Author> allAuthors, List<Author> someAuthors) {
		this.book = book;
		this.point = point;
		this.allAuthors = allAuthors;
		this.someAuthors = someAuthors;
	}

	public Book getBook() {
		return book;
	}

	public List<Author> getAllAuthors() {
		return allAuthors;
	}
	
	public List<Author> getSomeAuthors() {
		return someAuthors;
	}
	
	@Override
	public Type<ShowBookPopupEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowBookPopupEventHandler handler) {
		handler.onShowBookPopup(this);
	}

}
