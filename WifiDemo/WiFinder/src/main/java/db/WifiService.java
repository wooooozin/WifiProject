package db;

import java.sql.*;
import java.util.List;

import model.PublicWifiInfo.Row;

public class WifiService {
	
	private static String url = "jdbc:mariadb://localhost:3306/wifi";
	private static String dbUserId = "wifiuser";
	private static String dbPassword = "wifi";
	
	public static void insertWifiInfo(List<Row> dataList) {
	    if (dataList.isEmpty()) {
	        return;
	    }
	    
	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try { 
	        connection = DriverManager.getConnection(url, dbUserId, dbPassword);
	        if (connection == null) {
	            throw new SQLException("Failed to establish a database connection.");
	        }
	        
	        StringBuilder sqlBuilder = new StringBuilder();
	        sqlBuilder.append("INSERT INTO wifi_info ");
	        sqlBuilder.append("(distance, mgr_no, wrdofc, main_nm, adress1, adress2, instl_floor, instl_ty, ");
	        sqlBuilder.append("instl_mby, svc_se, cmcwr, cnstc_year, inout_door, remars3, lat, lnt, work_dttm) ");
	        sqlBuilder.append("VALUES (0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
	        sqlBuilder.append("ON DUPLICATE KEY UPDATE mgr_no = mgr_no");
	        
	        preparedStatement = connection.prepareStatement(sqlBuilder.toString());
	        
	        for (Row data : dataList) {
	            preparedStatement.setString(1, data.getX_SWIFI_MGR_NO());
	            preparedStatement.setString(2, data.getX_SWIFI_WRDOFC());
	            preparedStatement.setString(3, data.getX_SWIFI_MAIN_NM());
	            preparedStatement.setString(4, data.getX_SWIFI_ADRES1());
	            preparedStatement.setString(5, data.getX_SWIFI_ADRES2());
	            preparedStatement.setString(6, data.getX_SWIFI_INSTL_FLOOR());
	            preparedStatement.setString(7, data.getX_SWIFI_INSTL_TY());
	            preparedStatement.setString(8, data.getX_SWIFI_INSTL_MBY());
	            preparedStatement.setString(9, data.getX_SWIFI_SVC_SE());
	            preparedStatement.setString(10, data.getX_SWIFI_CMCWR());
	            preparedStatement.setString(11, data.getX_SWIFI_CNSTC_YEAR());
	            preparedStatement.setString(12, data.getX_SWIFI_INOUT_DOOR());
	            preparedStatement.setString(13, data.getX_SWIFI_REMARS3());
	            preparedStatement.setString(14, data.getLAT());
	            preparedStatement.setString(15, data.getLNT());
	            preparedStatement.setString(16, data.getWORK_DTTM());

	            preparedStatement.addBatch();
	        }
	        
	        int[] affectedRows = preparedStatement.executeBatch();
	        for (int i : affectedRows) {
	        	System.out.println(i);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	        
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
	
	public static void updateDistance(String latitude, String longitude, List<Row> dataList) {
	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;

	    try {
	        connection = DriverManager.getConnection(url, dbUserId, dbPassword);
	        if (connection == null) {
	            throw new SQLException("Failed to establish a database connection.");
	        }

	        String sql = "UPDATE wifi_info "
	        		+ " SET distance = SQRT(POW(69.1 * (lat - ?), 2) + POW(69.1 * (? - lnt) * COS(lat / 57.3), 2)) "
	        		+ " WHERE mgr_no = ? ";

	        preparedStatement = connection.prepareStatement(sql);
	        for (Row data : dataList) {
	            double lat = Double.parseDouble(latitude);
	            double lnt = Double.parseDouble(longitude);
	            
	            preparedStatement.setDouble(1, lat);
	            preparedStatement.setDouble(2, lnt);
	            preparedStatement.setString(3, data.getX_SWIFI_MGR_NO());
	            
	            preparedStatement.addBatch();
	        }
	        System.out.println(dataList.size());
	        int affectedRows = preparedStatement.executeUpdate();
	        System.out.println("업데이트된 거리 정보 수: " + affectedRows);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
	
	public static boolean hasData() {
	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    boolean hasData = false;

	    try {
	        connection = DriverManager.getConnection(url, dbUserId, dbPassword);
	        if (connection == null) {
	            throw new SQLException("Failed to establish a database connection.");
	        }

	        String sql = "SELECT COUNT(*) FROM wifi_info";

	        preparedStatement = connection.prepareStatement(sql);
	        rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            if (count > 0) {
	                hasData = true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    return hasData;
	}
	
	public static void main(String[] args) {

	}
}
