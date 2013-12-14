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
	
	@Test
	public void testSorting(){
		Voting v = new Voting();
		Voting canton1 = new Voting();
		canton1.setCantonName("ZH");
		Voting canton2 = new Voting();
		canton2.setCantonName("GE");
		Voting canton3 = new Voting();
		canton3.setCantonName("TI");
		
		v.addCanton(canton1);
		v.addCanton(canton2);
		v.addCanton(canton3);
		
		v.sortCantons(0, 2);
		
		assertEquals(v.getCantons().get(0).getCantonName(), "GE");
		assertEquals(v.getCantons().get(1).getCantonName(), "TI");
		assertEquals(v.getCantons().get(2).getCantonName(), "ZH");
		}

}
