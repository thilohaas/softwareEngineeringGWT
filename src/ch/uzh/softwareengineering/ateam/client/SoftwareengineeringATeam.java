package ch.uzh.softwareengineering.ateam.client;

import java.util.Date;

import ch.uzh.softwareengineering.ateam.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SoftwareengineeringATeam implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Create a panel to hold all of the form widgets.
		
		VerticalPanel mainpanel = new VerticalPanel();
		mainpanel.setWidth("1024px");
		mainpanel.setSpacing(10);
		
		VerticalPanel headerpanel = new VerticalPanel();
		headerpanel.setHeight("70px");
		headerpanel.setSpacing(25);
		HorizontalPanel uploadpanel = new HorizontalPanel();
		
		uploadpanel.setSpacing(8);
		
		HorizontalPanel contentpanel = new HorizontalPanel();
		contentpanel.setWidth("650px");
		contentpanel.setHeight("600px");
		contentpanel.setSpacing(20);
		
		StackPanel optionpanel = new StackPanel();
		optionpanel.setWidth("200px");
		
		VerticalPanel commentpanel = new VerticalPanel();
		commentpanel.setWidth("200px");
		commentpanel.setHeight("400px");
		commentpanel.setBorderWidth(2);
		
		Label votingHeader = new Label("Voting App");
		votingHeader.setStyleName("appHeader");
		
		FlowPanel sharepanel = new FlowPanel();
		sharepanel.setWidth("1024px");
		
		//create a FormPanel 
		final FormPanel form = new FormPanel();
		//create a file upload widget
		final FileUpload fileUpload = new FileUpload();
			
		//create labels
		Label selectLabel = new Label("Select a file:");
		//create upload button
		Button uploadButton = new Button("Upload File");
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
		
		ListBox yearList = new ListBox();
		yearList.addItem("all");
		yearList.addItem("2013");
		yearList.addItem("2012");
		yearList.addItem("2011");
		yearList.setVisibleItemCount(4);
		
		ListBox votingList = new ListBox();
		votingList.addItem("all votings");
		votingList.addItem("SampleVoting# 00245"); //should be replaced by a method or a for loop for each voting
		
		VerticalPanel zoomPanel = new VerticalPanel();
		Label zoomLabel = new Label("Select a View:");
		RadioButton nationalViewRB = new RadioButton("zommGroup", "National View");
		RadioButton kantonalViewRB = new RadioButton("zommGroup", "Kantonal View");
		nationalViewRB.setChecked(true);
		zoomPanel.add(zoomLabel);
		zoomPanel.add(nationalViewRB);
		zoomPanel.add(kantonalViewRB);
		
		optionpanel.add(yearList, "Year:");
		optionpanel.add(votingList, "Voting:");
		optionpanel.add(zoomPanel, "Zoomoption:");
		
		final Image mapImage = new Image();
		mapImage.setUrl("http://upload.wikimedia.org/wikipedia/commons/c/ca/BlankMap-Switzerland.png");
		
		//Sample Voting
		Voting example = new Voting();
		example.setId(0);
		Date date = new Date();
		date.setYear(2013);
		example.setDate(date);
		example.setTitle("Abzockerinitiative");
		example.setYesVotes(68.0);
		example.setNoVotes(32.0);
		
		Data data = new Data();
		data.addVoting(example);
		
		Visualisation visual = new Visualisation();
		visual.setData(data);
		FlexTable tabViewTable = visual.visualize();
		
		/*
		FlexTable tabViewTable = new FlexTable();
		tabViewTable.setTitle("Tabelaric View");
		tabViewTable.setText(0, 0, "Voting name:");
		tabViewTable.setText(0, 1, "Voting date:");
		tabViewTable.setText(0, 2, "'Yes' votes(%):");
		tabViewTable.setText(0, 3, "'No' votes(%):");
		//method to fill up the table
		*/
		TabPanel tabPanel = new TabPanel();
		tabPanel.setWidth("650px");
		tabPanel.add(tabViewTable, "Tabelaric View");
		tabPanel.add(mapImage, "Graphical View");
		tabPanel.selectTab(0);
		
		Label commLabel = new Label("Add Comments or Images:");
		VerticalPanel commButtonsPanel = new VerticalPanel();
		Button addComment = new Button("Add comment");
		Button addImage = new Button("Add image");
		Button deleteComment = new Button("Delete selected");
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
		
		Button shareViz = new Button("Share vizualisation");
		Button saveViz = new Button("Download vizualisation");
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

		// Add form to the root panel.     
		
		form.add(mainpanel);

		RootPanel.get("voting").add(form);

		final VotingServiceAsync votingService = (VotingServiceAsync) GWT.create(VotingService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) votingService;
		String moduleRelativeURL = GWT.getModuleBaseURL() + "votingService";
		endpoint.setServiceEntryPoint(moduleRelativeURL);

		@SuppressWarnings("rawtypes")
		final AsyncCallback userCallback = new AsyncCallback(){

			public void onFailure(Throwable caught) {
				alert("Sorry there was an error: " + caught.getMessage());
			}

			public void onSuccess(Object result) {
				Voting[] votings = (Voting[]) result;

				// don't be tempted to use the new for loop -- GWT does not support Java 5 in client code!
				for (int i=0;i<votings.length;i++) {
					Voting voting = votings[i];
				}

				alert("Got " + votings.length + " votings!");
			}

		};

		votingService.getVotings(userCallback);
	}

	public static native void alert(String msg) /*-{
  		$wnd.alert(msg);
  	}-*/;
}
