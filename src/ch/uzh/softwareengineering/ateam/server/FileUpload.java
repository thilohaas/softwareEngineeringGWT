package ch.uzh.softwareengineering.ateam.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ch.uzh.softwareengineering.ateam.client.Voting;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gwt.user.client.rpc.InvocationException;

public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		System.out.println("Got a new request for a fileupload!");

		if (!isMultipart) {
			System.out.println("Form is not multipart, abort!");
			return;
		}

		System.out.println("Ok form is multipart, continue...");
		try {
			this.removeAllEntriesFromDatabase();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		
		System.out.println("deleted all existing voting entries..");
		try {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory(10240, new File(""));
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			List items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				System.out.println("found a file.. lets check it..");
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					boolean isInMemory = item.isInMemory();
					long sizeInBytes = item.getSize();
					System.out.println("whuat? " + contentType + " " + fieldName +  " " + fileName + " mem: " + isInMemory + " si:" + sizeInBytes);
				} else {
					String fileName = item.getName();
					System.out.println("Got file " + fileName);
					if (fileName != null && !fileName.equals("")) {
						try {
							InputStreamReader streamReader = new InputStreamReader(item.getInputStream());
							BufferedReader br = null;
							String line = "";
							String cvsSplitBy = ",";

							System.out.println(item.getFieldName());

							try {

								br = new BufferedReader(streamReader);
								int i = 1;
								while ((line = br.readLine()) != null) {
									// use comma as separator
									String[] data = line.split(cvsSplitBy);

									if(data.length != 5) {
										System.out.println("Skipping line because length is not 5 its " + data.length + "! line: " + line);
										continue;
									}
									
									Voting v = new Voting();
									if(data[0].equals("National")){
										v.setNational(true);
										v.setCantonName("National");
										v.setTitle(data[1]);
										v.setYear(Integer.parseInt(data[2]));
										v.setYesVotes(Double.parseDouble(data[3]));
										v.setNoVotes(Double.parseDouble(data[4]));
										System.out.println(i++ + ": Added voting entry " + v.getTitle() + "...");
										this.addVoting(v);
									}
									else{
										v.setNational(false);
										v.setCantonName(data[0]);
										v.setTitle(data[1]);
										v.setYear(Integer.parseInt(data[2]));
										v.setYesVotes(Double.parseDouble(data[3]));
										v.setNoVotes(Double.parseDouble(data[4]));
										System.out.println(i++ + ": Added voting entry " + v.getCantonName() + " " + v.getTitle() + "...");
										this.addVoting(v);
									}
								}

							} catch (FileNotFoundException e) {
								throw new RuntimeException(e);
							} catch (IOException e) {
								throw new RuntimeException(e);
							} finally {
								if (br != null) {
									try {
										br.close();
									} catch (IOException e) {
										throw new RuntimeException(e);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}


					}
				}
			}
			
			System.out.println("Successfully finnished data upload!");

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void removeAllEntriesFromDatabase() throws Exception {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Use class Query to assemble a query
		Query q = new Query("voting");

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
			datastore.delete(result.getKey());
		}
	}

	public void addVoting(Voting voting) {
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity vote = new Entity("voting");
			vote.setProperty("title", voting.getTitle());
			vote.setProperty("year", voting.getYear());
			vote.setProperty("yes", voting.getYesVotes());
			vote.setProperty("no", voting.getNoVotes());
			vote.setProperty("canton", voting.getCantonName());
			datastore.put(vote);
		} catch (Exception e) {
			throw new InvocationException(
					"Exeption connecting to the database", e);
		}
	}
	
	public void getCantons(BufferedReader br, String line, String cvsSplitBy, Voting vote){
		int i = 0;
		try{
			while((line = br.readLine()) != null && i < 26){
				Voting v = new Voting();
				String[] data = line.split(cvsSplitBy);
				
				if(data.length != 5) {
					System.out.println("Skipping line because length is not 5 its " + data.length + "! line: " + line);
					continue;
				}
				System.out.println("Reading vote " + data[1]);
				v.setNational(false);
				v.setTitle(data[1]);
				v.setYear(Integer.parseInt(data[2]));
				v.setYesVotes(Double.parseDouble(data[3]));
				v.setNoVotes(Double.parseDouble(data[4]));
				System.out.println("oke");
				vote.addCanton(v);
				i++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
}