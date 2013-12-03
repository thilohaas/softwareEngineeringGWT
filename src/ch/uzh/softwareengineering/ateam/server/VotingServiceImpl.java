package ch.uzh.softwareengineering.ateam.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ch.uzh.softwareengineering.ateam.client.Voting;
import ch.uzh.softwareengineering.ateam.client.VotingService;

import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class VotingServiceImpl extends RemoteServiceServlet implements VotingService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final String QUERY_ALL = "select * FROM votings";

	private Connection connection;

	public ArrayList<Voting> getVotings() {
		ArrayList<Voting> votings = new ArrayList<Voting>();
		try {
			Connection conn = this.getConnection();
			PreparedStatement ps = conn.prepareStatement(QUERY_ALL);
			ResultSet results = ps.executeQuery();
			
			while (results.next()){
				votings.add(this.getVotingFromDatabaseResult(results));
			}
			// If this was "production" code then this would be in a finally block, etc.
			conn.close();
		} catch (SQLException e){
			throw new InvocationException("Exeption querying database",e);
		} catch (Exception e) {
			throw new InvocationException("Exeption connecting to the database",e);
		}
		
		System.out.println("got " + votings.size());

		return votings;
	}

    private Voting getVotingFromDatabaseResult(ResultSet results) throws SQLException {
    	Voting voting = new Voting();

		voting.setId(results.getInt(1));
		voting.setTitle(results.getString(2));
		voting.setYear(results.getInt(3));
		voting.setYesVotes(results.getDouble(4));
		voting.setNoVotes(results.getDouble(5));

		return voting;
	}

	private Connection getConnection() throws Exception {
    	if(null != this.connection && !this.connection.isClosed()) {
    		return this.connection;
    	}

    	try
        {
            String host          = "jdbc:mysql://tori.smartive.ch/";
            String db           = "thilo_softwareengineering";
            String driver       = "com.mysql.jdbc.Driver";
            String user         = "thilo_se";
            String pass         = "thilo_se$";

            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(host+db, user, pass);

        }
        catch(Exception e)
        {
            throw e;
        }

    	return this.connection;
    }

}
