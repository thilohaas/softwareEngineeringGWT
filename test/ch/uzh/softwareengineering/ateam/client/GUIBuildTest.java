package ch.uzh.softwareengineering.ateam.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class GUIBuildTest {

	@Test
	public void test() {
		VotingGUI gui = new VotingGUI();
		gui.buildGUI();
	}

}
