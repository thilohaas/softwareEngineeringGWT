package ch.uzh.softwareengineering.ateam.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("rawtypes")
public interface VotingServiceAsync {
	public void getVoting(int id, AsyncCallback callback);
	public void getVotings(AsyncCallback callback);
	public void addVoting(Voting voting, AsyncCallback callback);
	
}