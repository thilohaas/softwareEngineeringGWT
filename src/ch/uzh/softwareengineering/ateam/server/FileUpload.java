package ch.uzh.softwareengineering.ateam.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import com.google.gwt.user.client.rpc.InvocationException;

public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String INSERT_VOTING = "insert into votings (id, title, year, yesVotes, noVotes) VALUES (null, ?, ?, ?, ?)";
	private static final String DELETE_VOTINGS = "TRUNCATE TABLE votings";

	private Connection connection;

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
			FileItemFactory factory = new DiskFileItemFactory();
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
								while ((line = br.readLine()) != null) {
									// use comma as separator
									String[] data = line.split(cvsSplitBy);

									if(data.length != 4) {
										System.out.println("Skipping line because length is not 4 its " + data.length + "! line: " + line);
										continue;
									}

									Voting v = new Voting();
									v.setTitle(data[0]);
									v.setYear(Integer.parseInt(data[1]));
									v.setYesVotes(Double.parseDouble(data[2]));
									v.setNoVotes(Double.parseDouble(data[3]));
									
									System.out.println("Added voting entry " + v.getTitle() + "...");

									this.addVoting(v);
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

			if(!this.connection.isClosed()) {
				this.connection.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void removeAllEntriesFromDatabase() throws Exception {
		Connection conn = this.getConnection();
		PreparedStatement ps = conn.prepareStatement(DELETE_VOTINGS);
		ps.execute();
	}

	public void addVoting(Voting voting) {
		try {
			Connection conn = this.getConnection();

			PreparedStatement ps = conn.prepareStatement(INSERT_VOTING);
			ps.setString(1, voting.getTitle());
			ps.setInt(2, voting.getYear());
			ps.setDouble(3, voting.getYesVotes());
			ps.setDouble(4, voting.getNoVotes());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new InvocationException("Exeption saving stocks", e);
		} catch (Exception e) {
			throw new InvocationException(
					"Exeption connecting to the database", e);
		}
	}

	private Connection getConnection() throws Exception {
		if(null != this.connection && !this.connection.isClosed()) {
			return this.connection;
		}

		try {
			String host = "jdbc:mysql://tori.smartive.ch/";
			String db = "thilo_softwareengineering";
			String driver = "com.mysql.jdbc.Driver";
			String user = "thilo_se";
			String pass = "thilo_se$";

			Class.forName(driver).newInstance();
			this.connection = DriverManager
					.getConnection(host + db, user, pass);

		} catch (Exception e) {
			throw e;
		}

		return this.connection;
	}
}