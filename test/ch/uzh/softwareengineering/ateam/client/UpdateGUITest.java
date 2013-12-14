package ch.uzh.softwareengineering.ateam.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class UpdateGUITest {

	@Test
	public void test() {
		VotingGUI appGUI = new VotingGUI();
		appGUI.buildGUI(null);
		appGUI.updateGUI(false);	
	}

}
