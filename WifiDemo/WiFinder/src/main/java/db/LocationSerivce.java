package db;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Location;


public class LocationSerivce {
	private static String url = "jdbc:mariadb://localhost:3306/wifi";
	private static String dbUserId = "wifiuser";
	private static String dbPassword = "wifi";
	
	public static void insertLocationInfo(String latitude, String longitude) {
		if (latitude.isEmpty() || longitude.isEmpty()) {
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
	        
	        LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String currentTime = now.format(formatter);
	        
	        String sql = " INSERT INTO location "
	        		+ " (location_lat, location_lnt, confirm_date) "
	        		+ " VALUES (?, ?, ?) ; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, latitude);
            preparedStatement.setString(2, longitude);
            preparedStatement.setString(3, currentTime);
            
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
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
	
	public static List<Location> getLocalSearchHistory() {
		List<Location> locations = new ArrayList<>();
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
            String sql = " SELECT * " +
                    " FROM location " +
                    " ORDER BY location_id DESC ";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                String locationId = rs.getString("location_id");
                String lat = rs.getString("location_lat");
                String lnt = rs.getString("location_lnt");
                String date = rs.getString("confirm_date");
                
                Location location = new Location();
                location.setLocationId(locationId);
                location.setLatitude(lat);
                location.setLongitude(lnt);
                location.setConfirmDate(date);
                System.out.println(location.getLocationId());
              
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // 커넥션 해제
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (!preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
	    return locations;
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

	        String sql = "SELECT COUNT(*) FROM location";

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
	

}
