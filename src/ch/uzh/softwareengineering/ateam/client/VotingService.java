package ch.uzh.softwareengineering.ateam.client;

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.ArrayList;

public interface VotingService extends RemoteService {
	public ArrayList<Voting> getVotings();
}