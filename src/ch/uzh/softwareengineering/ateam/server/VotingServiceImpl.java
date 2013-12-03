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
				Voting voting = new Voting();

				voting.setTitle((String) result.getProperty("title"));
				voting.setYear(((Long)result.getProperty("year")).intValue());
				voting.setYesVotes((Double) result.getProperty("yes"));
				voting.setNoVotes((Double) result.getProperty("no"));
				
				votings.add(voting);
			}
		} catch (Exception e) {
			throw new InvocationException("Exeption connecting to the database",e);
		}
		
		System.out.println("got " + votings.size());

		return votings;
	}
}
