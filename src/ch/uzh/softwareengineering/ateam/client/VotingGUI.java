package ch.uzh.softwareengineering.ateam.client;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.collections.ListUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class VotingGUI {
	final Visualisation visual = new Visualisation();
	FlexTable tabViewTable = new FlexTable();
	Data data;
	private boolean zoomOption = false; 
	
	final ListBox yearList = new ListBox();
	final ListBox votingList = new ListBox(true);
	
	void buildGUI(ArrayList<Voting> dataset) {
		System.out.println("buildGUI got dataset #" + dataset.size());
		data = new Data(dataset);
		// Create a panel to hold all of the form widgets.
		
		final VerticalPanel mainpanel = new VerticalPanel();
		mainpanel.setWidth("1024px");
		mainpanel.setSpacing(10);
		
		final VerticalPanel headerpanel = new VerticalPanel();
		headerpanel.setHeight("70px");
		headerpanel.setSpacing(25);
		
		final HorizontalPanel uploadpanel = new HorizontalPanel();
		uploadpanel.setSpacing(8);
		uploadpanel.addStyleName("uploadPanel");
		
		final HorizontalPanel contentpanel = new HorizontalPanel();
		contentpanel.setWidth("650px");
		contentpanel.setHeight("600px");
		contentpanel.setSpacing(20);
		
		final StackPanel optionpanel = new StackPanel();
		optionpanel.setWidth("200px");
		
		final VerticalPanel commentpanel = new VerticalPanel();
		commentpanel.setWidth("200px");
		commentpanel.setHeight("400px");
		commentpanel.setBorderWidth(2);
		
		final Label votingHeader = new Label("Vote Result App");
		votingHeader.setStyleName("appHeader");
		
		final FlowPanel sharepanel = new FlowPanel();
		sharepanel.setWidth("1024px");
		
		
		yearList.addItem("all");
		yearList.addItem("2013");
		yearList.addItem("2012");
		//yearList.addItem("2011");
		yearList.setVisibleItemCount(3);
		

		votingList.addItem("all votings");
		votingList.setVisibleItemCount(10);
		
		final VerticalPanel zoomPanel = new VerticalPanel();
		final Label zoomLabel = new Label("Select a View:");
		final RadioButton nationalViewRB = new RadioButton("zommGroup", "National View");
		final RadioButton kantonalViewRB = new RadioButton("zommGroup", "Kantonal View");
		nationalViewRB.setChecked(true);
		zoomPanel.add(zoomLabel);
		zoomPanel.add(nationalViewRB);
		zoomPanel.add(kantonalViewRB);
		
		optionpanel.add(yearList, "Year:");
		optionpanel.add(votingList, "Voting:");
		optionpanel.add(zoomPanel, "Zoomoption:");
		
		final Image mapImage = new Image();
		mapImage.setUrl("http://upload.wikimedia.org/wikipedia/commons/c/ca/BlankMap-Switzerland.png");
		mapImage.setWidth("650px");
		mapImage.setHeight("412px");
		
		
		visual.setData(data);
		tabViewTable = visual.visualize();
		
		//add all votings to dropdown list
		int numOfVotings = data.getSize();
		for (int i = 0; i < numOfVotings; i++){
			String currentVoteName = data.getVoting(i).getTitle();
			votingList.addItem(currentVoteName);
		}

		final TabPanel tabPanel = new TabPanel();
		tabPanel.setWidth("650px");
		tabPanel.add(tabViewTable, "Tabular View");
		tabPanel.add(mapImage, "Graphical View");
		tabPanel.selectTab(0);
		
		final Label commLabel = new Label("Add Comments or Images:");
		final VerticalPanel commButtonsPanel = new VerticalPanel();
		final Button addComment = new Button("Add comment");
		final Button addImage = new Button("Add image");
		final Button deleteComment = new Button("Delete selected");
		commButtonsPanel.add(addComment);
		commButtonsPanel.add(addImage);
		commButtonsPanel.add(deleteComment);
		commentpanel.add(commLabel);
		commentpanel.add(commButtonsPanel);
		
		headerpanel.add(votingHeader);
		headerpanel.addStyleName("votingHeader");
		
		contentpanel.add(optionpanel);
		contentpanel.add(tabPanel);
		contentpanel.add(commentpanel);
		contentpanel.addStyleName("votingContent");
		
		final Button shareViz = new Button("Share visualisation");
		final Button saveViz = new Button("Download visualisation");
		sharepanel.add(saveViz);
		sharepanel.add(shareViz);
		
		mainpanel.add(headerpanel);
		mainpanel.add(contentpanel);
		mainpanel.add(sharepanel);
		

		votingList.setItemSelected(0, true);
		
		
		yearList.addChangeHandler(new ChangeHandler()
		{
			@Override
			public void onChange(ChangeEvent event) {

				votingList.setSelectedIndex(0);
				updateGUI(zoomOption);
			}

		});
		
		votingList.addChangeHandler(new ChangeHandler()
		{
			@Override
			public void onChange(ChangeEvent event) {
				updateGUI(zoomOption);
			}

		});
		
		nationalViewRB.addClickHandler(new ClickHandler() 
		{
			@Override
			public void onClick(ClickEvent event){
				zoomOption = false;
				updateGUI(zoomOption);
			}
		});
		
		
		kantonalViewRB.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				zoomOption = true;
				updateGUI(zoomOption);
			}
		});
		
		
		
		//create a FormPanel 
		final FormPanel form = new FormPanel();
		headerpanel.add(form);
		//create a file upload widget
		final FileUpload fileUpload = new FileUpload();
		fileUpload.setName("votingFileupload");
			
		//create labels
		final Label selectLabel = new Label("Select a file:");
		//create upload button
		final Button uploadButton = new Button("Upload File");
		//pass action to the form to point to service handling file 
		//receiving operation.
		form.setAction(GWT.getModuleBaseURL()+"fileupload");
		// set form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		
		uploadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//get the filename to be uploaded
				String filename = fileUpload.getFilename();
				if (filename.length() == 0) {
					Window.alert("No File Specified!");
				} else {
					//submit the form
					form.submit();			          
				}				
			}
		});

		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this 
				//event is fired. Assuming the service returned a response 
				//of type text/html, we can get the result text here
				Window.alert("Data successfully updated! Please reload the page.");
			}
		});
		
		form.add(uploadpanel);

		//add a label
		uploadpanel.add(selectLabel);
		//add fileUpload widget
		uploadpanel.add(fileUpload);
		//add a button to upload the file
		uploadpanel.add(uploadButton);

		RootPanel.get("voting").add(mainpanel);
	}
	
	//method is called after every change in the option panel to update the view
	void updateGUI(boolean isCantonalView){
		ArrayList<Voting> filteredData = new ArrayList<Voting>();
		ArrayList<Voting> yearFilteredData = new ArrayList<Voting>();
		ArrayList<Voting> selectFilteredData = new ArrayList<Voting>();
		
		int selctedYearIndex = yearList.getSelectedIndex();
		int selectedYear = 0;
		int numOFVotes = data.getSize();
		
		System.out.println("updateGUI called with data #"+numOFVotes);
		
		if(selctedYearIndex != 0){
			selectedYear = Integer.parseInt(yearList.getItemText(selctedYearIndex));
			//filtering for year
			for(int i = 0; i < numOFVotes; i++){
				if(selectedYear == data.getVotings().get(i).getYear()){
					yearFilteredData.add(data.getVotings().get(i));
				}
			}
		}
		else{
			for(int i = 0; i < numOFVotes; i++){
				yearFilteredData.add(data.getVotings().get(i));
			}
		}
		filteredData = yearFilteredData;
		
		
		boolean[] selectedArray = new boolean[votingList.getItemCount()];
		//checking which votings are selected
		for(int i = 0; i < votingList.getItemCount(); i++){
			if(votingList.isItemSelected(i)){
				selectedArray[i] = true;
			}
			else{
				selectedArray[i] = false;
			}
		}
		//refresh voting dropdownlist
		int itemInListCount = votingList.getItemCount();
		votingList.clear();
		votingList.addItem("all votings");
		for (int i = 0; i < yearFilteredData.size(); i++){
			String currentVoteName = yearFilteredData.get(i).getTitle();
			votingList.addItem(currentVoteName);
		}
		for(int j = 0; j < itemInListCount; j++){
			if(selectedArray[j] == true){
				votingList.setItemSelected(j, true);
			}
		}
		
		//filter selected votes
		if(selectedArray[0] == false){
			for(int i = 1; i <= yearFilteredData.size(); i++){
				if(selectedArray[i] == true){
					selectFilteredData.add(yearFilteredData.get(i-1));
				}
			}
		}
		else{
			selectFilteredData = yearFilteredData;
		}
		
		filteredData = selectFilteredData;
		
		//actually updating the table view
		if(isCantonalView){
			updateTableCantonal(tabViewTable, filteredData);
		}
		else{
			updateTable(tabViewTable, filteredData);
		}
		
	}
	
	void updateTable(FlexTable table, ArrayList<Voting> currData) {
		System.out.println("Updating table with #" + currData.size());
		table.removeAllRows();
		table.setText(0, 0, "Year");
		table.setText(0, 1, "Name");
		table.setText(0, 2, "Yes-Votes(%)");
		table.setText(0, 3, "No-Votes(%)");
		for(int i = 1 ; i <= currData.size(); i++){
			int date = currData.get(i-1).getYear();
			String year = String.valueOf(date);
			table.setText(i, 0, year);
			table.setText(i, 1, currData.get(i -1).getTitle());
			String yesVotes = String.valueOf(currData.get(i -1).getYesVotes());
			table.setText(i, 2, yesVotes);
			String noVotes = String.valueOf(currData.get(i -1).getNoVotes());
			table.setText(i, 3, noVotes);
		}
	}
	
	void updateTableCantonal(FlexTable table, ArrayList<Voting> currData){
		System.out.println("Updating table with #" + currData.size() + " and all Cantons");
		table.removeAllRows();
		table.setText(0, 0, "Year");
		table.setText(0, 1, "Name");
		table.setText(0, 2, "Yes-Votes(%)");
		table.setText(0, 3, "No-Votes(%)");
		
		int size = 0;
		for(int i = 0; i < currData.size(); i++){
			size += currData.get(i).getCantons().size();
			size++;
		}
		
		for(int i = 1; i <= size; i++){
			for(int j = 0; j < currData.size(); j++){
				int date = currData.get(j).getYear();
				String year = String.valueOf(date);
				table.setText(i, 0, year);
				table.setText(i, 1, currData.get(j).getTitle());
				String yesVotes = String.valueOf(currData.get(j).getYesVotes());
				table.setText(i, 2, yesVotes);
				String noVotes = String.valueOf(currData.get(j).getNoVotes());
				table.setText(i, 3, noVotes);
				i++;
				for(int k = 0; k < currData.get(j).getCantons().size(); k++){
					int date_ = currData.get(j).getCantons().get(k).getYear();
					String year_ = String.valueOf(date_);
					table.setText(i, 0, year_);
					table.setText(i, 1, currData.get(j).getCantons().get(k).getCantonName());
					String yesVotes_ = String.valueOf(currData.get(j).getCantons().get(k).getYesVotes());
					table.setText(i, 2, yesVotes_);
					String noVotes_ = String.valueOf(currData.get(j).getCantons().get(k).getNoVotes());
					table.setText(i, 3, noVotes_);
					i++;
				}
			}
		}
	}
}
