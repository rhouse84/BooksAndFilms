package booksandfilms.client.view;

import booksandfilms.client.entities.Author;
import booksandfilms.client.presenter.AuthorEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AuthorEditView extends Composite implements AuthorEditPresenter.Display {

	private static AuthorEditUiBinder uiBinder = GWT.create(AuthorEditUiBinder.class);

	interface AuthorEditUiBinder extends UiBinder<Widget, AuthorEditView> {}

	@UiField TextBox nameField;
	@UiField Button cancelButton, saveButton;
	
	Author currentAuthor = new Author();
	
    public AuthorEditView() {
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
    
    public HasClickHandlers getSaveButton() {
    	return saveButton;
    }

}
