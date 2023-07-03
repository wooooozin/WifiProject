package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Bookmark;
import model.Wifi;

public class BookmarkService {
	private static String url = "jdbc:mariadb://localhost:3306/wifi";
	private static String dbUserId = "wifiuser";
	private static String dbPassword = "wifi";
	
	public static void insertBookmarkInfo(String bookmarkName, String priority) {
		if (bookmarkName.isEmpty() || priority.isEmpty()) {
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
	        
	        String sql = " INSERT INTO bookmark_info "
	        		+ " (bookmark_name, priority, regit_date, modif_date) "
	        		+ " VALUES (?, ?, ?, ?) ; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookmarkName);
            preparedStatement.setInt(2, Integer.parseInt(priority));
            preparedStatement.setString(3, currentTime);
            preparedStatement.setString(4, "");
            
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
	
	public static List<Bookmark> showBookmarkInfoOderByPriority() {
		List<Bookmark> bookmarks = new ArrayList<>();
		
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

	        String sql = " SELECT * FROM bookmark_info "
	        		+ " ORDER BY priority; ";

	        preparedStatement = connection.prepareStatement(sql);
	        rs = preparedStatement.executeQuery(); 
           
	        while (rs.next()) {
                String id = rs.getString("bookmark_id");
                String name = rs.getString("bookmark_name");
                int priority = rs.getInt("priority");
                String regitDate = rs.getString("regit_date");
                String modifDate = rs.getString("modif_date");
                
                
                Bookmark bookmark = new Bookmark();
                bookmark.setBookmarkId(id);
                bookmark.setBookmarkName(name);
                bookmark.setPriority(String.valueOf(priority));
                bookmark.setRegitDate(regitDate);
                bookmark.setModifDate(modifDate);
                bookmarks.add(bookmark);
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
	    
		return bookmarks;
		
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

	        String sql = "SELECT COUNT(*) FROM bookmark_info";

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
