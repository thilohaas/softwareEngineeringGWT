package ch.uzh.softwareengineering.ateam.client;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DataTest {

	/**
	 * Test method
	 */
	@Test
	public void testData() {
		Voting voting1 = new Voting();
		Voting voting2 = new Voting();
		Voting voting3 = new Voting();
		
		voting1.setTitle("Abzockerinitiative");
		
		ArrayList<Voting> list = new ArrayList<Voting>();
		list.add(voting1);
		list.add(voting2);
		list.add(voting3);
		
		Data data = new Data(list);
		
		assertEquals(3, data.getSize());
		assertEquals("Abzockerinitiative", data.getVoting(0).getTitle());
	}
}
