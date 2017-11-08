package cn.edu.tsinghua.PostBack;

import java.sql.*;

public class Derby {
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String protocol = "jdbc:derby:";
	String dbName = "F:\\university\\学\\毕设\\postBack\\Derby";

	public void getDataFromDerby() throws Exception {
		Class.forName(driver).newInstance();
		Connection conn = DriverManager.getConnection(protocol + dbName + ";create=true");
		Statement statement = conn.createStatement();
		statement.execute("create table test1(id int,name varchar(20)");
		statement.execute("insert into test1 values(1,'sinboy')");
		statement.execute("inert into test1 values(2,'Tom')");
		statement.execute("create table test1(name varchar(20),timestamp int");
		statement.execute("insert into test1 values('file1',20171108153212)");
		statement.execute("inert into test1 values('file2', 20171108153215)");
		ResultSet resultSet = statement.executeQuery("select * from test1");
		while (resultSet.next()) {
			System.out.println("name:" + resultSet.getString(1) + "  timestamp:" + resultSet.getInt(2));
		}
		conn.close();
		statement.close();
	//	resultSet.close();
		

	}

	public static void main(String[] args) throws Exception {
		Derby derbyTest = new Derby();
		derbyTest.getDataFromDerby();
	}
}
