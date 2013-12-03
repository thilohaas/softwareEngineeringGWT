/**
 * 
 */
package ch.uzh.softwareengineering.ateam.client;

import static org.junit.Assert.*;

import org.junit.Test;


public class VotingTest {
	@Test
	public void testId() {
		Voting v = new Voting();
		v.setId(2334);
		assertEquals(v.getId(), 2334);
	}

	@Test
	public void testTitle() {
		Voting v = new Voting();
		v.setTitle("myTitle");
		assertEquals(v.getTitle(), "myTitle");
	}

	@Test
	public void testYear() {
		Voting v = new Voting();
		v.setYear(2013);
		assertEquals(v.getYear(), 2013);
	}
	
	@Test
	public void testYesVotes() {
		Voting v = new Voting();
		v.setYesVotes(23.45);
		assertEquals(v.getYesVotes(), 23.45, 0.001);
	}
	
	@Test
	public void testNoVotes() {
		Voting v = new Voting();
		v.setNoVotes(13.75);
		assertEquals(v.getNoVotes(), 13.75, 0.001);
	}

}
