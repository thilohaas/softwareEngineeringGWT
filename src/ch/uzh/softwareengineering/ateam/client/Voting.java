package ch.uzh.softwareengineering.ateam.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Voting implements IsSerializable {
	private int id;
	private String title;
	private Date date;
	private String type;
	private double yesVotes = 0;
	private double noVotes = 0;
	private double participation = 0;
	
	public Voting() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the yesVotes
	 */
	public double getYesVotes() {
		return yesVotes;
	}

	/**
	 * @param yesVotes the yesVotes to set
	 */
	public void setYesVotes(double yesVotes) {
		this.yesVotes = yesVotes;
	}

	/**
	 * @return the noVotes
	 */
	public double getNoVotes() {
		return noVotes;
	}

	/**
	 * @param noVotes the noVotes to set
	 */
	public void setNoVotes(double noVotes) {
		this.noVotes = noVotes;
	}

	/**
	 * @return the participation
	 */
	public double getParticipation() {
		return participation;
	}

	/**
	 * @param participation the participation to set
	 */
	public void setParticipation(double participation) {
		this.participation = participation;
	}
}
