<%@page import="db.LocationSerivce"%>
<%@page import="model.Location"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위치 히스토리 목록</title>
<style type="text/css">
.location_top_buttons {
	margin-top: 15px;
	margin-bottom: 15px;
}

#wifi_table {
	font-family: Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#wifi_table td, #customers th {
	border: 1px solid #ddd;
	padding: 8px;
}

#wifi_table tr:nth-child(even) {
	background-color: #f2f2f2;
}

#wifi_table tr:hover {
	background-color: #ddd;
}

#wifi_table th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	background-color: #04AA6D;
	color: white;
	border: 1px solid white;
}

#wifi_table td {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	color: black;
	border: 1px solid lightgray;
}
</style>
</head>

<body>
	<h1>위치 히스토리 목록</h1>

	<div class="location_top_buttons">
		<a href="main.jsp">홈</a> | <a href="location-history.jsp">위치 히스토리
			목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
	</div>
	<table id="wifi_table">
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>

		<tbody>
			<%
			if (!LocationSerivce.hasData()) {
			%>
			<tr>
				<td colspan="5" style="text-align: center;">조회한 위치 정보가 없습니다.</td>
			</tr>
			<%
			} else {
			%>

			<%
				List<Location> locations = LocationSerivce.getLocalSearchHistory();
				for (Location location : locations) {
			%>

			<tr>
				<td><%= location.getLocationId() %></td>
				<td><%= location.getLatitude() %></td>
				<td><%= location.getLongitude() %></td>
				<td><%= location.getConfirmDate() %></td>
				<td><button type="button" onclick="">삭제</button></td>	
			</tr>
			<% 
			} 
			%>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>