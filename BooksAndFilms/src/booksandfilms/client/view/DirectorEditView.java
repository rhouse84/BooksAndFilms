package booksandfilms.client.view;

import booksandfilms.client.entities.Director;
import booksandfilms.client.presenter.DirectorEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class DirectorEditView extends Composite implements DirectorEditPresenter.Display {

	private static DirectorEditUiBinder uiBinder = GWT.create(DirectorEditUiBinder.class);

	interface DirectorEditUiBinder extends UiBinder<Widget, DirectorEditView> {}

	@UiField TextBox nameField;
	@UiField Button cancelButton, saveButton;
	
	Director currentDirector = new Director();
	
    public DirectorEditView() {
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
