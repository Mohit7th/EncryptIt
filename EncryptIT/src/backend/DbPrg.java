/*	
 * database connectivity code that will check for valid users in the database
 *	date : 10-5-2015, 15-05-2015
 */
package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Create table userlist(uid varchar2(5) primary key,uname varchar2(16) unique, upassword varchar2(16))

public class DbPrg {

	// database connection code
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CONN_STRING = "jdbc:mysql://localhost/encryptit";

	// retrieved data
	private String uname, passwd, tableName;
	private int meth;

	public DbPrg(String uname, String passwd, String tableName, int meth) {
		this.uname = uname;
		this.passwd = passwd;
		this.tableName = tableName;
		this.meth = meth;
	}

	public boolean dbConnect() throws ClassNotFoundException {
		if (meth == 1)
			createDb();
		// TODO Auto-generated method stub
		boolean connOrNot = false;

		// database connection code
		Class.forName("com.mysql.jdbc.Driver");
		// close objects in reverse order of there creation
		try (Connection conn = DriverManager.getConnection(CONN_STRING,
				USERNAME, PASSWORD);
				Statement stmt = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery("select uname, passwd from "
						+ tableName)) {

			// retrieving username and password for comparison
			if (this.meth == 1)
				connOrNot = checkUserDetails(rs);

		} catch (SQLException e) {
			System.out.println(e);
		}
		return connOrNot;
	}

	// method that will compare each password and username with the entered
	// details
	private boolean checkUserDetails(ResultSet rs) throws SQLException {
		while (rs.next()) {
			if (rs.getString("uname").equals(this.uname)) {
				if (rs.getString("passwd").equals(this.passwd)) {
					return true;
				}
			}
		}
		return false;
	}

	private static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static String dbName = "encryptit";

	// method to create a database and a table, on second execution generates
	// exception
	private void createDb() throws ClassNotFoundException {

		Class.forName(jdbcDriver);
		try (Connection con = DriverManager
				.getConnection("jdbc:mysql://localhost/?user=root&password=");
				Statement st = con.createStatement();) {
			String sql = "CREATE DATABASE " + dbName;
			st.executeUpdate(sql);

			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + dbName, "root", "");
					Statement stmt = conn.createStatement();) {

				// create table and a default root user that will grant access
				// to create a new user
				sql = "create table if not exists users(uname varchar( 16 ) UNIQUE, passwd varchar( 16 ))";
				stmt.executeUpdate(sql);

				sql = "create table if not exists filedata(uname varchar(16) , filename varchar(100), data TEXT)";
				stmt.executeUpdate(sql);

				// inserting a default user for the software user
				sql = "INSERT INTO users( uname, passwd )VALUES ('root', 'root')";
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				System.out.println("Table already created!");
			}
		} catch (Exception e) {
			System.out.println("Database already created!");
			return;
		}
	}

	// adding user through root or any other user
	public boolean addNewUser() throws ClassNotFoundException {
		Class.forName(jdbcDriver);
		try (Connection con = DriverManager.getConnection(CONN_STRING,
				USERNAME, PASSWORD); Statement st = con.createStatement();) {
			String sql = "INSERT INTO users(uname, passwd) values('" + uname
					+ "','" + passwd + "')";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("DbPrg (addNewUser) : " + e);
			return false;
		}
		return true;
	}

	// changing user password by updating it
	public boolean changeMyPassword() throws ClassNotFoundException {
		Class.forName(jdbcDriver);
		try (Connection con = DriverManager.getConnection(CONN_STRING,
				USERNAME, PASSWORD); Statement st = con.createStatement();) {
			String sql = "UPDATE users set passwd = '" + passwd
					+ "' WHERE uname = '" + uname + "'";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("DbPrg (changeMyPassword) : " + e);
			return false;
		}
		return true;
	}

	// saving encryted text into a database for future use
	public boolean saveFilesEncryptedDataToDb(String textData, String filename)
			throws ClassNotFoundException {
		Class.forName(jdbcDriver);
		try (Connection con = DriverManager.getConnection(CONN_STRING,
				USERNAME, PASSWORD); Statement st = con.createStatement();) {
			String sql = "INSERT INTO filedata(uname, fileName, data) values('"
					+ uname + "','" + filename + "','" + textData + "')";
			st.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("DbPrg (SaveFilesEncryptedDataToDb) : " + e);
			return false;
		}
		return true;
	}

	// method to get all the encrypted data related to a user
	public Object[][] dataReturned() throws ClassNotFoundException {
		Object data[][] = null;
		Class.forName("com.mysql.jdbc.Driver");
		// close objects in reverse order of there creation
		try (Connection conn = DriverManager.getConnection(CONN_STRING,
				USERNAME, PASSWORD);
				Statement stmt = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery("select filename, data from "
						+ tableName + " WHERE uname = '" + uname + "'")) {

			rs.last();
			// getting total rows to create a 2d array of that size
			int total = rs.getRow();
			rs.beforeFirst();

			// making at either zero or 2 row array
			if (total == 1)
				data = new Object[total + 1][total + 1];
			else
				data = new Object[total][total];

			// storing all the data in the 2d array which will be used and
			// displayed in the viewdata frame
			for (int i = 0; rs.next(); i++) {
				data[i][0] = rs.getString("filename");
				data[i][1] = rs.getString("data");
			}

		} catch (SQLException e) {
			System.out.println("DbPrg (dataReturned) : " + e);
		}
		return data;
	}
}
