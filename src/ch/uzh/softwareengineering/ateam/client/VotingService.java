package ch.uzh.softwareengineering.ateam.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface VotingService extends RemoteService {
	public Voting getVoting(int id);
	public Voting[] getVotings();
	public Voting addVoting(Voting voting);
}