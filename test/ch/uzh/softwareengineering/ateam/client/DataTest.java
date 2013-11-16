package ch.uzh.softwareengineering.ateam.client;

import static org.junit.Assert.*;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

public class DataTest {

	@Test
	public void test() {
		Voting voting1 = new Voting();
		Voting voting2 = new Voting();
		Voting voting3 = new Voting();
		
		voting1.setTitle("Abzockerinitiative");
		
		Data data = new Data();
		data.addVoting(voting1);
		data.addVoting(voting2);
		data.addVoting(voting3);
		
		assertEquals(3, data.getSize());
		assertTrue(data.getVoting(1).getTitle().equals("Abzockerinitiative"));
		
		
	}

}
