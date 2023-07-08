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
import model.BookmarkList;

public class BookmarkListSerivce {
	
	private static String url = "jdbc:mariadb://localhost:3306/wifi";
	private static String dbUserId = "wifiuser";
	private static String dbPassword = "wifi";
	
	
	// 정보 조회하기 
	public static List<BookmarkList> showBookmarkLists() {
		List<BookmarkList> bookmarkLists = new ArrayList<>();
		
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

	        String sql = " SELECT bl.bookmark_list_id, b.bookmark_name, w.main_nm, bl.regit_date FROM bookmark_list bl, wifi_info w, bookmark_info b "
	        		+ "WHERE bl.wifi_id = w.wifi_id "
	        		+ "AND bl.bookmark_id = b.bookmark_id ; ";

	        preparedStatement = connection.prepareStatement(sql);
	        rs = preparedStatement.executeQuery(); 
           
	        while (rs.next()) {
                String id = rs.getString("bookmark_list_id");
                String bookmarkName = rs.getString("bookmark_name");
                String wifiName = rs.getString("main_nm");
                String regitDate = rs.getString("regit_date");
                
                
                BookmarkList bookmarkList = new BookmarkList();
                bookmarkList.setBookmarkListId(id);
                bookmarkList.setBookmarkName(bookmarkName);
                bookmarkList.setWifiMainName(wifiName);
                bookmarkList.setRegitDateS(regitDate);
                
                bookmarkLists.add(bookmarkList);
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
	    
		return bookmarkLists;
		
	}
	
	// 데이터 베이스 추가
	public static void insertBookmarkInfo(String wifiId, String bookmarkId) {
		if (wifiId.isEmpty() || bookmarkId.isEmpty()) {
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
	        
	        String sql = " INSERT INTO bookmark_list (wifi_id, bookmark_id, regit_date) "
	        		+ " VALUES( ?, ?,? ) ; ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(wifiId));
            preparedStatement.setInt(2, Integer.parseInt(bookmarkId));
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
	
	// 삭제 하기 
	public static void deleteLocationInfo(String id) {
		try {
	        Class.forName("org.mariadb.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    
	    try {
            // 커넥션 객체 생성
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
            // 쿼리 생성
            String sql = " DELETE FROM bookmark_list "
            		+ "WHERE bookmark_list_id = ? ; ";

            int idValue = Integer.parseInt(id);
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idValue);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
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
	}
	
	
	// 데이터 있나 없나 확인
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

	        String sql = "SELECT COUNT(*) FROM bookmark_list";

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
