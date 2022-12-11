package cafe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnector {
	private static final String driverPath = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/cafe_pos?severTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
	private static final String id = "root";
	private static final String pwd = "1234";
	private static Connection con;
	
	public static Connection getCon() {
		try {
			Class.forName(driverPath);
			System.out.println("Driver가 정상적으로 로드되었습니다.");
			
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("연결이 정상적으로 되었습니다.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void rsTest() {
		try {
			Statement stmt = con.createStatement();
			String sql = "select * from menu";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(rs.getString("menu_name"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		JDBCConnector dl = new JDBCConnector();
		dl.getCon();
		dl.rsTest();
	}
}

