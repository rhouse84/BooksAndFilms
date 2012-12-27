package booksandfilms.client.widget;

import java.util.Collection;

import com.google.gwt.user.client.ui.HasValue;

public interface HasSelectedValue<T> extends HasValue<T> {
	void setSelections(Collection<T> selections);
	void setSelectedValue(T selected);
	T getSelectedValue();
}
