package annotationreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnect {

	public static Connection connect(){

		Connection con = null;
		Properties props = new Properties();
		props.setProperty("user", "root");
		props.setProperty("password", "");

		try {


			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/question_marking_system", props);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		System.out.println("Success");

		return con;

	}
}

