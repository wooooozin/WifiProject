<%@page import="api.Api"%>
<%@page import="db.LocationSerivce"%>
<%@page import="model.PublicWifiInfo"%>
<%@page import="model.PublicWifiInfo.Row"%>
<%@page import="model.Wifi"%>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="db.WifiService"%>
<%@ page import="db.LocationSerivce"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<link href="css/main.css" rel="stylesheet">
<script src="js/find-location.js"></script>
</head>

<body>
	<h1>와이파이 정보 구하기</h1>

	<div class="top_menu">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> <span>|</span> 
		<a href="">북마크 보기</a> <span>|</span>
		<a href="">북마크 그룹 관리</a> <span>|</span>
	</div>
	<form action="main.jsp" method="post" class="form_main">
		<div class="input_fields">
			<div class="input_item">
				<label for="latField">LAT : </label> <input type="text"
					id="latField" name="latField">
			</div>
			<span>,</span>
			<div class="input_item">
				<label for="lntField">LNT : </label> <input type="text"
					id="lntField" name="lntField">
			</div>
			<div class="btn_area">
				<button type="button" onclick="getUserLocation()">내 위치 가져오기</button>
				<button type="submit">근처 WIFI 정보보기</button>
			</div>
		</div>
	</form>

	<%
	request.setCharacterEncoding("UTF-8");
	String lat = request.getParameter("latField");
	String lnt = request.getParameter("lntField");
	if (lat != null && lnt != null) {
		LocationSerivce.insertLocationInfo(lat, lnt);
		int totalCount = Api.getTotalCount();
		int batchSize = 1000;
		for (int start = 1; start <= totalCount; start += batchSize) {
			int end = Math.min(start + batchSize - 1, totalCount);

			PublicWifiInfo wifiInfo = Api.getWifiInfo(start, end);
			List<Row> infoList = wifiInfo.getTbPublicWifiInfo().getRow();
			WifiService.updateDistance(lat, lnt, infoList);
		}
	}
	%>

	<table id="wifi_table" class="wifi_table">
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>

		<tbody>
			<%
			if (WifiService.hasData()) {
				if (lat == null && lnt == null) {
			%>
			<tr>
				<td colspan="17" style="text-align: center;">위치 정보를 입력한 후에 조회해
					주세요.</td>
			</tr>
			<%
			} else {
			List<Wifi> wifis = WifiService.showOrderByDistance();
			for (Wifi wifi : wifis) {
			%>
			<tr>
				<td><%=wifi.getDistance()%></td>
				<td><%=wifi.getManagerNumber()%></td>
				<td><%=wifi.getWardOffice()%></td>
				<td><%=wifi.getMainName()%></td>
				<td><%=wifi.getAddress1()%></td>
				<td><%=wifi.getAddress2()%></td>
				<td><%=wifi.getInstallationFloor()%></td>
				<td><%=wifi.getInstallationType()%></td>
				<td><%=wifi.getInstallationBy()%></td>
				<td><%=wifi.getServiceType()%></td>
				<td><%=wifi.getNetworkType()%></td>
				<td><%=wifi.getConstructionYear()%></td>
				<td><%=wifi.getIndoorOutdoor()%></td>
				<td><%=wifi.getWifiEnvironment()%></td>
				<td><%=wifi.getLatitude()%></td>
				<td><%=wifi.getLongitude()%></td>
				<td><%=wifi.getWorkDateTime()%></td>
			</tr>
			<%
					}
				}
			} else {
			%>
			<tr>
				<td colspan="17" style="text-align: center;">Open API 와이파이 정보를
					가져온 후 조회해 주세요.</td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>

</body>
</html>