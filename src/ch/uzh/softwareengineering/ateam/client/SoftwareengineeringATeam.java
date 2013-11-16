package ch.uzh.softwareengineering.ateam.client;

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

	/**
	 * This is the entry point method.
	 */
	
	public void onModuleLoad() {
		VotingGUI appGUI = new VotingGUI();
		appGUI.buildGUI();

		final VotingServiceAsync votingService = (VotingServiceAsync) GWT.create(VotingService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) votingService;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "votingService";
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		@SuppressWarnings("rawtypes")
		final AsyncCallback userCallback = new AsyncCallback(){

			public void onFailure(Throwable caught) {
				alert("Sorry there was an error: " + caught.getMessage());
			}

			public void onSuccess(Object result) {
				Voting[] votings = (Voting[]) result;

				// don't be tempted to use the new for loop -- GWT does not support Java 5 in client code!
				for (int i=0;i<votings.length;i++) {
					Voting voting = votings[i];
				}

				alert("Got " + votings.length + " votings!");
			}

		};

		votingService.getVotings(userCallback);
	}

	

	public static native void alert(String msg) /*-{
  		$wnd.alert(msg);
  	}-*/;
}
