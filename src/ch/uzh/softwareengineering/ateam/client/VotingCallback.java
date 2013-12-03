package ch.uzh.softwareengineering.ateam.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("rawtypes")
public class VotingCallback implements AsyncCallback {
	
	private VotingGUI appGUI;
	
	public VotingCallback(VotingGUI appGUI) {
		this.appGUI = appGUI;
	}

	public void onFailure(Throwable caught) {
		alert("Sorry there was an error: " + caught.getMessage());
	}

	public void onSuccess(Object result) {
		@SuppressWarnings("unchecked")
		ArrayList<Voting> votings = (ArrayList<Voting>) result;

		// don't be tempted to use the new for loop -- GWT does not support Java 5 in client code!
//		for (int i=0;i<votings.length;i++) {
//			Voting voting = votings[i];
//		}

		this.appGUI.buildGUI(votings);
		this.appGUI.updateGUI();
	}
	
	public static native void alert(String msg) /*-{
		$wnd.alert(msg);
	}-*/;

}
