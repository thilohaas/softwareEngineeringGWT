package ch.uzh.softwareengineering.ateam.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFormat 
{
	private long votingNr;
	private Date votingday;
	
	private String votingSubject;
	private String language;
	private String region;
	private String kanton;
	
	private int subscribedVoters;
	private int realVoters;
	private double percentRealVoters;
	
	private int validatedVote;
	private int voteWithYes;
	private double percentVoteWithYes;
	
	
	private DataFormat() 
	{
		votingNr = System.nanoTime();
		votingSubject="None";
	}
	
	public DataFormat(String aSubject, String aLanguage, String aRegion, String aKanton ) {
		this();
		setVotingSubject(aSubject);
		setLanguage(aLanguage);
		setRegion(aRegion);
		setKanton(aKanton);
	}

	public long getVotingNr() {
		return votingNr;
	}
	
	public String getVotingSubject() {
		return votingSubject;
	}
	
	public void setVotingSubject(String aSubject) {
		this.votingSubject = aSubject;
	}
	
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String aLanguage) {
		this.language = aLanguage;
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String aRegion) {
		this.region = aRegion;
	}
	
	public String getKanton() {
		return kanton;
	}

	public void setKanton(String aKanton) {
		this.kanton = aKanton;
	}
	
	public int getSubscribedVoters() {
		return subscribedVoters;
	}

	public void setSubscribedVoters(int aSubscribedVoters) {
		this.subscribedVoters = aSubscribedVoters;
	}
	
	public int getRealVoters() {
		return realVoters;
	}

	public void setRealVoters(int arealVoters) {
		this.realVoters = arealVoters;
	}
	//
	public double getPercentRealVoters() {
		return ((double)realVoters)/subscribedVoters;
	}
    
	public int getValidatedVote() {
		return validatedVote;
	}

	public void setValidatedVote(int avalidatedVote) {
		this.validatedVote = avalidatedVote;
	}
	
	public int getVoteWithYes() {
		return voteWithYes;
	}

	public void setVoteWithYes(int avoteWithYes) {
		this.voteWithYes = avoteWithYes;
	}
	
	public double getPercentVoteWithYes() {
		return ((double)voteWithYes)/validatedVote;
	}

	public void setVotingday(int day, int month, int year) {
		// Calendar.getInstance() returns an instance of a subclass of Calendar,
		// based on the locally used calendar type
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		// converting date to Unix time representation
		votingday = calendar.getTime();
	}

	public Date getVotingday() {
		return votingday;
	}
	
	@Override
	public String toString() {
		// StringBuilder allows appending more Strings
		StringBuilder builder = new StringBuilder();
		
		builder.append(votingNr).append(" ").append(".\t")
			   .append(votingSubject);
		
		if(votingday != null) {
			// With SimpleDateFormat, it is possible to 
			// format Date into desired String representation
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			builder.append(" ").append('\t')
				   .append(df.format(votingday));
		}
		
		builder.append(" ").append(language).append(" ").append(".\t")
		   .append(kanton).append(" ").append(".\t")
		   .append(region).append(" ").append(".\t");
		
		if(subscribedVoters != 0) {
			builder.append(subscribedVoters).append(" ").append('\t')
			       .append(realVoters).append(" ").append('\t')
			       .append(percentRealVoters).append(" ").append('\t');
		}
		
		if(validatedVote != 0) {
			builder.append(validatedVote).append(" ").append('\t')
			       .append(voteWithYes).append(" ").append('\t')
			       .append(percentVoteWithYes).append(" ").append('\t');
		}	
		
		return builder.toString();
	}
	
	
	
}
