package booksandfilms.client.view;

import booksandfilms.client.entities.UserAccount;
import booksandfilms.client.presenter.UserEditPresenter;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class UserEditView extends Composite implements UserEditPresenter.Display {

	private static UserEditUiBinder uiBinder = GWT.create(UserEditUiBinder.class);

	interface UserEditUiBinder extends UiBinder<Widget, UserEditView> {}

	@UiField TextBox nameField;
	@UiField TextBox emailField;
	@UiField Label lastLoginField;
	@UiField ListBox adminField;
	@UiField Button cancelButton, saveButton;
	
	UserAccount currentUser = new UserAccount();
	
    public UserEditView() {
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

	@Override
	public HasValue<String> getEmail() {
		return emailField;
	}

	@Override
	public HasText getLastLogin() {
		return lastLoginField;
	}

	public void setAdminData(Boolean admin) {
		adminField.addItem("True","True");
		adminField.addItem("False","False");
		if (admin) {
			adminField.setSelectedIndex(0);
		} else {
			adminField.setSelectedIndex(1);
		}
	}

	@Override
	public String getAdminValue() {
		return adminField.getValue(adminField.getSelectedIndex());
	}

}
