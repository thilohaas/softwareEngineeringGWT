package ch.uzh.softwareengineering.ateam.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class GUIBuildTest {

	@Test
	public void testBuildGui() {
		VotingGUI gui = new VotingGUI();
		gui.buildGUI();
	}

}
