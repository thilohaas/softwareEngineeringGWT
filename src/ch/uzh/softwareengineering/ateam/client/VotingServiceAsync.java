package ch.uzh.softwareengineering.ateam.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("rawtypes")
public interface VotingServiceAsync {
	public void getVotings(AsyncCallback callback);
	
}