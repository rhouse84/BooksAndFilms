package booksandfilms.client.helper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;

import booksandfilms.client.BooksAndFilms;
import booksandfilms.client.event.LogoutEvent;
import booksandfilms.client.event.RPCInEvent;
import booksandfilms.client.event.RPCOutEvent;

import booksandfilms.shared.exception.NotLoggedInException;

import booksandfilms.shared.SharedConstants;

public abstract class RPCCall<T> implements AsyncCallback<T> {
	
	protected abstract void callService(AsyncCallback<T> cb);
	
	private void call(final int retriesLeft) {
		onRPCOut();
		
		callService(new AsyncCallback<T>() {
			public void onFailure(Throwable caught) {
				onRPCIn();
				GWT.log(caught.toString(), caught);
				try {
					throw caught;
				} catch (InvocationException invocationException) {
					if(caught.getMessage().equals(SharedConstants.LOGGED_OUT)) {
						BooksAndFilms.get().getEventBus().fireEvent(new LogoutEvent());
						return;
					}
					if (retriesLeft <= 0) {
						RPCCall.this.onFailure(invocationException);
					} else {
						call(retriesLeft - 1); // retry call
					}
				} catch (IncompatibleRemoteServiceException remoteServiceException) {
					Window.alert("The app maybe out of date. Reload this page in your browser.");
				} catch (SerializationException serializationException) {
					Window.alert("A serialization error occurred. Try again.");
				} catch (NotLoggedInException e) {
					BooksAndFilms.get().getEventBus().fireEvent(new LogoutEvent());
				} catch (RequestTimeoutException e) {
					Window.alert("This is taking too long, try again");
				} catch (Throwable e) {
					GWT.log("caught an exception e = "+e.getMessage());
					RPCCall.this.onFailure(e);
				}
			}
			
			public void onSuccess(T result) {
				onRPCIn();
				RPCCall.this.onSuccess(result);
			}
		});
	}
	
	private void onRPCIn() {
		//GWT.log("onRPCIn");
		BooksAndFilms.get().getEventBus().fireEvent(new RPCInEvent());
	}
	
	private void onRPCOut() {
		//GWT.log("onRPCOut");
		BooksAndFilms.get().getEventBus().fireEvent(new RPCOutEvent());
	}
	
	public void retry(int retryCount) {
		//GWT.log("retry count "+retryCount);
		call(retryCount);
	}
}
