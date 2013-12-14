package ch.uzh.softwareengineering.ateam.server;

import java.util.ArrayList;

import ch.uzh.softwareengineering.ateam.client.Voting;
import ch.uzh.softwareengineering.ateam.client.VotingService;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class VotingServiceImpl extends RemoteServiceServlet implements VotingService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public ArrayList<Voting> getVotings() {
		ArrayList<Voting> votings = new ArrayList<Voting>();
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			// Use class Query to assemble a query
			Query q = new Query("voting");

			// Use PreparedQuery interface to retrieve results
			PreparedQuery pq = datastore.prepare(q);

			for (Entity result : pq.asIterable()) {
				//System.out.println("Got vote: " + result.getProperty("canton") + " " + result.getProperty("title"));
				if(((String) result.getProperty("canton")).equals("National")){
					Voting voting = new Voting();
					voting.setTitle((String) result.getProperty("title"));
					voting.setYear(((Long)result.getProperty("year")).intValue());
					voting.setYesVotes((Double) result.getProperty("yes"));
					voting.setNoVotes((Double) result.getProperty("no"));
					voting.setCanton("National");
					ArrayList<Voting> cantons = getCantons(voting.getTitle(), pq);
					voting.setCantons(cantons);
					votings.add(voting);
				}
			}
		} catch (Exception e) {
			throw new InvocationException("Exeption connecting to the database",e);
		}
		
		System.out.println("got " + votings.size());

		return votings;
	}
	
	public ArrayList<Voting> getCantons(String vote, PreparedQuery pq){
		ArrayList<Voting> cantons = new ArrayList<Voting>();
		for	(Entity result : pq.asIterable()){
			if(((String) result.getProperty("title")).equals(vote)){
				if(!(((String) result.getProperty("canton")).equals("National"))){
					Voting voting = new Voting();
					voting.setTitle((String) result.getProperty("title"));
					voting.setYear(((Long)result.getProperty("year")).intValue());
					voting.setYesVotes((Double) result.getProperty("yes"));
					voting.setNoVotes((Double) result.getProperty("no"));
					voting.setCanton((String) result.getProperty("canton"));
					voting.setCantons(null);
					cantons.add(voting);
				}
			}
		}
		return cantons;
	}
}
