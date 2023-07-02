package db;

import java.lang.management.OperatingSystemMXBean;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;

import model.Location;
import model.PublicWifiInfo.Row;
import model.Wifi;

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
	        sqlBuilder.append("(mgr_no, wrdofc, main_nm, adress1, adress2, instl_floor, instl_ty, ");
	        sqlBuilder.append("instl_mby, svc_se, cmcwr, cnstc_year, inout_door, remars3, lat, lnt, work_dttm) ");
	        sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
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
	            preparedStatement.setString(14, data.getLNT());
	            preparedStatement.setString(15, data.getLAT());
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
	                + "SET distance = (ROUND(6371 * acos(cos(radians(?)) * cos(radians(lat)) * cos(radians(lnt) - radians(?)) + sin(radians(?)) * sin(radians(lat))), 4)) "
	                + "WHERE mgr_no = ? ; ";

	        preparedStatement = connection.prepareStatement(sql);
	        for (Row data : dataList) {
	            preparedStatement.setDouble(1, Double.parseDouble(latitude));
	            preparedStatement.setDouble(2, Double.parseDouble(longitude));
	            preparedStatement.setDouble(3, Double.parseDouble(latitude));
	            preparedStatement.setString(4, data.getX_SWIFI_MGR_NO());
	            preparedStatement.addBatch();
	        }

	        int[] affectedRows = preparedStatement.executeBatch();

	        System.out.println("업데이트된 거리 정보 수: " + affectedRows.length);
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
	
	public static List<Wifi> showOrderByDistance() {
		List<Wifi> wifis = new ArrayList<>();

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

	        String sql = " SELECT * FROM wifi_info "
	        		+ " ORDER BY CAST(distance AS double) LIMIT 20 ; ";

	        preparedStatement = connection.prepareStatement(sql);
	        rs = preparedStatement.executeQuery(); 
           
	        while (rs.next()) {
                String distance = rs.getString("distance");
                String mgrNo = rs.getString("mgr_no");
                String wrdofc = rs.getString("wrdofc");
                String mainNm = rs.getString("main_nm");
                String address1 = rs.getString("adress1");
                String address2 = rs.getString("adress2");
                String instlFloor = rs.getString("instl_floor");
                String instlTy = rs.getString("instl_ty");
                String instlMby = rs.getString("instl_mby");
                String svcSe = rs.getString("svc_se");
                String cmcwr = rs.getString("cmcwr");
                String cnstcYear = rs.getString("cnstc_year");
                String inoutDoor = rs.getString("inout_door");
                String remars3 = rs.getString("remars3");
                String lat = rs.getString("lat");
                String lnt = rs.getString("lnt");
                String workDttm = rs.getString("work_dttm");
                
                Wifi wifi = new Wifi();
                wifi.setDistance(distance);
                wifi.setManagerNumber(mgrNo);
                wifi.setWardOffice(wrdofc);
                wifi.setMainName(mainNm);
                wifi.setAddress1(address1);
                wifi.setAddress2(address2);
                wifi.setInstallationFloor(instlFloor);
                wifi.setInstallationType(instlTy);
                wifi.setInstallationBy(instlMby);
                wifi.setServiceType(svcSe);
                wifi.setNetworkType(cmcwr);
                wifi.setConstructionYear(cnstcYear);
                wifi.setIndoorOutdoor(inoutDoor);
                wifi.setWifiEnvironment(remars3);
                wifi.setLatitude(lat);
                wifi.setLongitude(lnt);
                wifi.setWorkDateTime(workDttm);
                              
                wifis.add(wifi);
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
	    return wifis;
	}
	
	public static Wifi showDetailWifiInfo(String manageNumber) {
		Wifi wifiInfo = new Wifi();
		
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

	        String sql = " SELECT * FROM wifi_info "
	        		+ "WHERE mgr_no = ? ; ";

	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, manageNumber);
	        rs = preparedStatement.executeQuery(); 
           
	        while (rs.next()) {
                String distance = rs.getString("distance");
                String mgrNo = rs.getString("mgr_no");
                String wrdofc = rs.getString("wrdofc");
                String mainNm = rs.getString("main_nm");
                String address1 = rs.getString("adress1");
                String address2 = rs.getString("adress2");
                String instlFloor = rs.getString("instl_floor");
                String instlTy = rs.getString("instl_ty");
                String instlMby = rs.getString("instl_mby");
                String svcSe = rs.getString("svc_se");
                String cmcwr = rs.getString("cmcwr");
                String cnstcYear = rs.getString("cnstc_year");
                String inoutDoor = rs.getString("inout_door");
                String remars3 = rs.getString("remars3");
                String lat = rs.getString("lat");
                String lnt = rs.getString("lnt");
                String workDttm = rs.getString("work_dttm");
                
                wifiInfo.setDistance(distance);
                wifiInfo.setManagerNumber(mgrNo);
                wifiInfo.setWardOffice(wrdofc);
                wifiInfo.setMainName(mainNm);
                wifiInfo.setAddress1(address1);
                wifiInfo.setAddress2(address2);
                wifiInfo.setInstallationFloor(instlFloor);
                wifiInfo.setInstallationType(instlTy);
                wifiInfo.setInstallationBy(instlMby);
                wifiInfo.setServiceType(svcSe);
                wifiInfo.setNetworkType(cmcwr);
                wifiInfo.setConstructionYear(cnstcYear);
                wifiInfo.setIndoorOutdoor(inoutDoor);
                wifiInfo.setWifiEnvironment(remars3);
                wifiInfo.setLatitude(lat);
                wifiInfo.setLongitude(lnt);
                wifiInfo.setWorkDateTime(workDttm);
                System.out.println(distance);

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
		
		return wifiInfo;
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
	
}
