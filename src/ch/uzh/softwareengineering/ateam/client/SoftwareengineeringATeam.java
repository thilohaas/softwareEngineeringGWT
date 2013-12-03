package ch.uzh.softwareengineering.ateam.client;

import java.util.ArrayList;
import java.util.Date;

import ch.uzh.softwareengineering.ateam.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SoftwareengineeringATeam implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	public final VotingGUI appGUI = new VotingGUI();

	/**
	 * This is the entry point method.
	 */
	
	public void onModuleLoad() {
		final VotingServiceAsync votingService = (VotingServiceAsync) GWT.create(VotingService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) votingService;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "votingService";
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		@SuppressWarnings("rawtypes")
		final AsyncCallback userCallback = new VotingCallback(appGUI);
		votingService.getVotings(userCallback);
	}

	

	public static native void alert(String msg) /*-{
  		$wnd.alert(msg);
  	}-*/;
}
