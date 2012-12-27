package booksandfilms.client.view;

import booksandfilms.client.entities.Quote;
import booksandfilms.client.presenter.QuoteEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class QuoteEditView extends Composite implements QuoteEditPresenter.Display {

	private static QuoteEditUiBinder uiBinder = GWT.create(QuoteEditUiBinder.class);

	interface QuoteEditUiBinder extends UiBinder<Widget, QuoteEditView> {}

	@UiField TextBox nameField;
	@UiField TextBox quoteField;
	@UiField Button cancelButton, saveButton;
	
	Quote currentQuote = new Quote();
	
    public QuoteEditView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @Override
    protected void onLoad() {
    	super.onLoad();
    	nameField.setFocus(true);
    }

    @Override
    public Widget asWidget() {
    	return this;
    }

    public HasClickHandlers getCancelButton() {
    	return cancelButton;
    }

    public HasValue<String> getName() {
    	return nameField;
    }
    
    public HasValue<String> getQuote() {
    	return quoteField;
    }
    
    public HasClickHandlers getSaveButton() {
    	return saveButton;
    }

}
