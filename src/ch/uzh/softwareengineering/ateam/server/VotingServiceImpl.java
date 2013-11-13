package ch.uzh.softwareengineering.ateam.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ch.uzh.softwareengineering.ateam.client.Voting;
import ch.uzh.softwareengineering.ateam.client.VotingService;

import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class VotingServiceImpl extends RemoteServiceServlet implements VotingService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String LOOKUP = "select * from voting where id=?";
	private static final String QUERY_ALL = 
		"select * FROM votings";
	private static final String INSERT_VOTING = "insert into voting (id, title, date, type, yesVotes, noVotes, participation) VALUES (null, ?, ?, ?, ?, ?, ?)";
	
	private Connection connection;
	
	public Voting getVoting(int id) {
		try {
			Connection conn = this.getConnection();
			PreparedStatement ps = conn.prepareStatement(LOOKUP);
			ps.setInt(1, id);
			ResultSet results = ps.executeQuery();
			if (results.next()){
				Voting v = this.getVotingFromDatabaseResult(results);
				conn.close();
				return v;
			}
			
		} catch (SQLException e) {
			throw new InvocationException("Exeption querying database",e);
		} catch (Exception e) {
			throw new InvocationException("Exeption connecting to the database",e);
		}
		
		throw new InvocationException("Voting " + id + " not found!");
	}

	public Voting addVoting(Voting voting) {
		int votingId = 0;
		
		try {
			Connection conn = this.getConnection();
			
			PreparedStatement ps = conn.prepareStatement(INSERT_VOTING);
			ps.setString(1, voting.getTitle());
			ps.setString(2, voting.getDate().toString());
			ps.setString(3, voting.getType());
			ps.setInt(4, voting.getYesVotes());
			ps.setInt(5, voting.getNoVotes());
			ps.setDouble(6, voting.getParticipation());
			
			int num = ps.executeUpdate();
			if (num > 0){
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()){
					votingId = rs.getInt(1);
				}
			}
			
			conn.close();
		} catch (SQLException e) {
			throw new InvocationException("Exeption saving stocks",e);
		} catch (Exception e) {
			throw new InvocationException("Exeption connecting to the database",e);
		}
		
		return this.getVoting(votingId);
	}

	public Voting[] getVotings() {
		try {
			Connection conn = this.getConnection();
			PreparedStatement ps = conn.prepareStatement(QUERY_ALL);
			ResultSet results = ps.executeQuery();
			
			Voting[] votings = new Voting[results.getFetchSize()];
			int i = 0;
			while (results.next()){
				votings[i] = this.getVotingFromDatabaseResult(results);
				i++;
			}
			// If this was "production" code then this would be in a finally block, etc.
			conn.close();
			
			return votings;
			
		} catch (SQLException e){
			throw new InvocationException("Exeption querying database",e);
		} catch (Exception e) {
			throw new InvocationException("Exeption connecting to the database",e);
		}
	}
    
    private Voting getVotingFromDatabaseResult(ResultSet results) throws SQLException {
    	Voting voting = new Voting();
    	
		voting.setId(results.getInt(1));
		voting.setTitle(results.getString(2));
		@SuppressWarnings("deprecation")
		Date d = new Date(results.getString(3));
		voting.setDate(d);
		voting.setType(results.getString(4));
		voting.setYesVotes(results.getInt(5));
		voting.setNoVotes(results.getInt(6));
		voting.setParticipation(results.getDouble(7));
		
		return voting;
	}

	private Connection getConnection() throws Exception {
    	if(null != this.connection) {
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
