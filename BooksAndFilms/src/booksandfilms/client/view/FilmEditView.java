package booksandfilms.client.view;

import java.util.ArrayList;
import java.util.Date;
import booksandfilms.client.entities.Book;
import booksandfilms.client.entities.Topic;
import booksandfilms.client.presenter.FilmEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class FilmEditView extends Composite implements FilmEditPresenter.Display {

	private static BookEditUiBinder uiBinder = GWT.create(BookEditUiBinder.class);

	interface BookEditUiBinder extends UiBinder<Widget, FilmEditView> {}

	@UiField TextBox titleField;
	@UiField TextBox yearField;
	@UiField TextArea notesField;
	@UiField TextArea starsField;
	@UiField ListBox ratingField;
	@UiField DateBox watchDateField;
	@UiField Label directorName;
	@UiField ListBox topicField;
	@UiField Button cancelButton, saveButton, todayButton;
	@UiField VerticalPanel quotePanel;

	Book currentBook = new Book();
	
	public FilmEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
    protected void onLoad() {
    	super.onLoad();
    	titleField.setFocus(true);
    }

    @Override
    public Widget asWidget() {
    	return this;
    }

    public HasValue<String> getTitleDesc() {
    	return titleField;
    }
    
    public HasValue<String> getYear() {
    	return yearField;
    }
    
    public HasValue<String> getNotes() {
    	return notesField;
    }
    
    public HasValue<String> getStars() {
    	return starsField;
    }
    
    public HasValue<Date> getWatchDate() {
    	return (HasValue<Date>) watchDateField;
    }
    
    public HasClickHandlers getSaveButton() {
    	return saveButton;
    }

    public HasClickHandlers getCancelButton() {
    	return cancelButton;
    }

	public HasText getDirectorName() {
		return directorName;
	}

    public String getTopicValue() {
    	if (topicField.getSelectedIndex() > 0) {
    		return topicField.getValue(topicField.getSelectedIndex());
    	} else {
    		return null;
    	}
    }
    
	@Override
	public String getTopicDesc() {
    	if (topicField.getSelectedIndex() > 0) {
    		return topicField.getItemText(topicField.getSelectedIndex());
    	} else {
    		return null;
    	}
	}

	@Override
	public void setRatingData(Byte rating) {
		ratingField.addItem("Bad", "1");
		ratingField.addItem("OK", "2");
		ratingField.addItem("Good", "3");
		ratingField.addItem("Great", "4");
		if (rating != null)
			ratingField.setSelectedIndex(rating.intValue()-1);
	}

	@Override
	public String getRatingValue() {
		return ratingField.getValue(ratingField.getSelectedIndex());
	}

	public void setTopicData(ArrayList<Topic> topics, Long topicId) {
		topicField.addItem("**No Topic Selected**", "");
		if (topics == null) {
			//do nothing
		} else {
			for (Topic topic : topics) {
				topicField.addItem(topic.getDescription(),topic.getId().toString());
			}
		}
		if (topicId == null) {
			topicField.setSelectedIndex(0);
		} else {
			for (int x = 0; x < topicField.getItemCount(); x++) {
				if (topicField.getValue(x).equals(topicId.toString())) {
					topicField.setSelectedIndex(x);
					break;
				}
			}
		}
		
	}

	@Override
	public Widget getQuotePanel() {
		return quotePanel;
	}

    public HasClickHandlers getTodayButton() {
    	return todayButton;
    }

}
