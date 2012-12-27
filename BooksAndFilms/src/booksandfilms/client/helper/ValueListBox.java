package booksandfilms.client.helper;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

/**
 * A ListBox implementation supporting the HasValue interface.
 * 
 * @author Lukasz Plotnicki <lukasz.plotnicki@med.uni-heidelberg.de>
 * @author Philip Ives <pives@philadev.com> added ability to insert values for setValue();
 * 
 */
public class ValueListBox extends ListBox implements HasValue<String> {

	/**
	 * Flag indicating if the handler have been already initialized or not
	 */
	private boolean valueChangeHandlerInitialized;
        /**
          * Flag to either add new values or don't set the selectedIndex at all
	* 
	*/
	private boolean addNewValuesToList;
	/**
	 * Default constructor.New values won't be added to listbox
	 */
	public ValueListBox( ){
	super();
	this.addNewValuesToList=false;
	}
	/**
	 * @param addNewValuesToList
	 */
	public ValueListBox(Boolean addNewValuesToList) {
		super();
		this.addNewValuesToList=addNewValuesToList;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#getValue()
	 */
	@Override
	public String getValue() {
		return getItemText(getSelectedIndex());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(String value) {
		int i;
		for (i = 0; i < getItemCount(); i++) {
			if (getItemText(i).equals(value)) {
				setSelectedIndex(i);
				break;
			}
		}
		if(i>= getItemCount()) {
			if (addNewValuesToList) {
				addItem(value);
				setSelectedIndex(getItemCount());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object,
	 * boolean)
	 */
	@Override
	public void setValue(String value, boolean arg1) {
		setValue(value);
		if (arg1)
			ValueChangeEvent.fire(this, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.google.gwt.event.logical.shared.HasValueChangeHandlers#
	 * addValueChangeHandler
	 * (com.google.gwt.event.logical.shared.ValueChangeHandler)
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		// Initialization code
		if (!valueChangeHandlerInitialized) {
			valueChangeHandlerInitialized = true;
			addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					ValueChangeEvent.fire(ValueListBox.this, getValue());
				}
			});
		}
		return addHandler(handler, ValueChangeEvent.getType());
	}

}