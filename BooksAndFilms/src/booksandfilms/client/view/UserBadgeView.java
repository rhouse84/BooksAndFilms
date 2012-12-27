package booksandfilms.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import booksandfilms.client.presenter.UserBadgePresenter;

public class UserBadgeView extends Composite implements UserBadgePresenter.Display {
	@UiField Label usernameLabel;
	@UiField Anchor logoutLink;
	@UiField Anchor topicLink;
	@UiField Anchor userLink;

	private static UserBadgeViewUiBinder uiBinder = GWT.create(UserBadgeViewUiBinder.class);

	interface UserBadgeViewUiBinder extends UiBinder<Widget, UserBadgeView> {}

	public UserBadgeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getLogoutLink() {
		return logoutLink;
	}

	public HasClickHandlers getTopicLink() {
		return topicLink;
	}

	public HasClickHandlers getUserLink() {
		return userLink;
	}

	public HasText getUsernameLabel() {
		return usernameLabel;
	}

	@Override
	public void hideUserLink() {
		userLink.setVisible(false);
	}

}
