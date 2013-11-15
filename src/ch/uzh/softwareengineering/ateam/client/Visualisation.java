package ch.uzh.softwareengineering.ateam.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlexTable;

enum Mode {TABELLARIC, GRAPHIC};

public class Visualisation {
	
	private Mode mode;
	
	private Data data;
	
	private FlexTable table; 
	
	public Visualisation(){
		mode = Mode.TABELLARIC;
	}
	
	public Data getData(/*Filter filter*/){
		Data data = new Data();    //Random operation
		return data;
	}
	
	public FlexTable visualize(){
		table = new FlexTable();
		table.setText(0, 0, "Year");
		table.setText(0, 1, "Name");
		table.setText(0, 2, "Yes-Votes");
		table.setText(0, 3, "No-Votes"); 
		
		return table;
	}
	
	
	
	
}
