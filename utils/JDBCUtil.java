package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class JDBCUtil {

	public static Connection getConnection() {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/soso_db";
		String username = "root";
		String pwd = "ANHUIaq0415";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, pwd);
		} catch(Exception e) {
			System.out.println("获取连接失败");
		}
		return conn;
	}

	public static void close(PreparedStatement statement,Connection conn) {
		if(statement != null){
			try{
				statement.close();
			}catch(Exception e){
				
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				
			}
		}
	}
}
