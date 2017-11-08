package cn.edu.tsinghua.PostBack;

import java.sql.*;
import java.util.*;
import org.apache.thrift.TException;

public class IFileReceiveImp implements IFileReceiverService.Iface {
	private Map<String, Integer> fileListFromReceiver;

	//根据发送端的uuid返回该路径下的文件列表
	public Map<String, Integer> prepare(String uuid) {
		Statement statement;
		try {
			//Derby嵌入式服务器的连接
			statement = new Derby1().connectToDerby();
			
			//从相应的文件夹(uuid)获取文件传输列表
			ResultSet resultSet = statement.executeQuery("select * from "+uuid);
			
			//将文件列表打包成Map格式返回文件列表
			while(resultSet.next())
			{
				fileListFromReceiver.put(resultSet.getString(1), resultSet.getInt(2));
				//System.out.println("name:" + resultSet.getString(1) + "  timestamp:" + resultSet.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileListFromReceiver;
	}

	public void startReceiving(Map<String, Integer> fileList) throws TException {
		// TODO Auto-generated method stub

	}

	public void afterReceiving() throws TException {
		// TODO Auto-generated method stub

	}

	public void cancelReceiving() throws TException {
		// TODO Auto-generated method stub

	}
}

class Derby1 {
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String protocol = "jdbc:derby:";
	private Connection conn;
	private Statement statement;
	String dbName = "F:\\university\\学\\毕设\\postBack\\Derby";

	public Statement connectToDerby() throws Exception {
		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(protocol + dbName + ";create=true");
		statement = conn.createStatement();
		return statement;
	}
	public void closeConnection() throws Exception
	{
		conn.close();
		statement.close();
	}
}
