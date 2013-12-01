package ch.uzh.softwareengineering.ateam.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	void buildGUI() {
		// Create a panel to hold all of the form widgets.
		
		final VerticalPanel mainpanel = new VerticalPanel();
		mainpanel.setWidth("1024px");
		mainpanel.setSpacing(10);
		
		final VerticalPanel headerpanel = new VerticalPanel();
		headerpanel.setHeight("70px");
		headerpanel.setSpacing(25);
		final HorizontalPanel uploadpanel = new HorizontalPanel();
		
		uploadpanel.setSpacing(8);
		
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
		
		//create a FormPanel 
		final FormPanel form = new FormPanel();
		//create a file upload widget
		final FileUpload fileUpload = new FileUpload();
			
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

		//add a label
		uploadpanel.add(selectLabel);
		//add fileUpload widget
		uploadpanel.add(fileUpload);
		//add a button to upload the file
		uploadpanel.add(uploadButton);
		
		final ListBox yearList = new ListBox();
		yearList.addItem("all");
		yearList.addItem("2013");
		yearList.addItem("2012");
		yearList.addItem("2011");
		yearList.setVisibleItemCount(4);
		
		final ListBox votingList = new ListBox(true);
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
		
		//Sample Voting
		final Voting example = new Voting();
		example.setId(0);
		Date date = new Date();
		date.setYear(2013);
		example.setDate(date);
		example.setTitle("Abzockerinitiative");
		example.setYesVotes(68.0);
		example.setNoVotes(32.0);
		
		final Data data = new Data();
		data.addVoting(example);
		
		final Visualisation visual = new Visualisation();
		visual.setData(data);
		final FlexTable tabViewTable = visual.visualize();
		
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
		headerpanel.add(uploadpanel);
		
		contentpanel.add(optionpanel);
		contentpanel.add(tabPanel);
		contentpanel.add(commentpanel);
		
		final Button shareViz = new Button("Share visualisation");
		final Button saveViz = new Button("Download visualisation");
		sharepanel.add(saveViz);
		sharepanel.add(shareViz);
		
		mainpanel.add(headerpanel);
		mainpanel.add(contentpanel);
		mainpanel.add(sharepanel);
		

		
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
				Window.alert(event.getResults());				
			}
		});
		
		// Add form to the root panel     
		form.add(mainpanel);

		RootPanel.get("voting").add(form);
	}
	//method is called after every change in the option panel to update the view
	void updateGUI(){
		
	}
	
}
