package ch.uzh.softwareengineering.ateam.client;

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
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
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
		HorizontalPanel panel = new HorizontalPanel();

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
		panel.add(selectLabel);
		//add fileUpload widget
		panel.add(fileUpload);
		//add a button to upload the file
		panel.add(uploadButton);
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
		form.add(panel);

		RootPanel.get("navbar_right_upload").add(form);

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
