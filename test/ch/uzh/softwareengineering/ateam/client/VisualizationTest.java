package ch.uzh.softwareengineering.ateam.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.FlexTable;

public class VisualizationTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testVisualisation() {
		Voting voting1 = new Voting();
		Voting voting2 = new Voting();
		Voting voting3 = new Voting();
		
		voting1.setTitle("Abzockerinitiative");
		
		Data data = new Data(null);
		
		Visualisation visual = new Visualisation();
		visual.setData(data);
		
		FlexTable table = visual.visualize();
		
		assertEquals(4, table.getRowCount());
		assertEquals(4, table.getCellCount(0));
		
	}

}
