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
	/**
	 * 
	 * @return Returns a table with all available votings. 
	 * 
	 */
	public FlexTable visualize(){
		//Create the table
		table = new FlexTable();
		table.setText(0, 0, "Year");
		table.setText(0, 1, "Name");
		table.setText(0, 2, "Yes-Votes");
		table.setText(0, 3, "No-Votes");
		
		//Fill it with the data
		for(int i = 0; i < data.getDataSet().length; i++){
			int date = data.getDataSet()[i].getDate().getYear();
			String year = String.valueOf(date);
			table.setText(i, 0, year);
			table.setText(i, 1, data.getDataSet()[i].getTitle());
			String yesVotes = String.valueOf(data.getDataSet()[i].getYesVotes());
			table.setText(i, 2, yesVotes);
			String noVotes = String.valueOf(data.getDataSet()[i].getNoVotes());
			table.setText(i, 3, noVotes);
		}
		
		return table;
	}
	
	
	
	
}
