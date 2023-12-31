<%@page import="api.Api"%>
<%@page import="db.LocationSerivce"%>
<%@page import="model.PublicWifiInfo"%>
<%@page import="model.PublicWifiInfo.Row"%>
<%@page import="model.Wifi"%>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<div id="loadingArea" class="loading_area">
	<div class="loading_inner">
		<div class="box dot">
			<span></span>
			<span></span>
			<span></span>	
		</div>
	</div>
</div>
	<h1>와이파이 정보 구하기</h1>
	<%
	request.setCharacterEncoding("UTF-8");
	String lat = request.getParameter("latField");
	String lnt = request.getParameter("lntField");
	if (lat != null && lnt != null) {
		LocationSerivce.insertLocationInfo(lat, lnt);
		List<Wifi> wifiManageNumbers = WifiService.selectIdNumberInfo();
		WifiService.updateDistance(lat, lnt, wifiManageNumbers);
	}
	%>
	<div class="top_menu">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp" id="loadWifiLink">Open API 와이파이 정보 가져오기</a> <span>|</span>
		<a href="show-bookmark.jsp">북마크 보기</a> <span>|</span>
		<a href="manage-bookmark.jsp">북마크 그룹 관리</a>
	</div>
	<form action="main.jsp" method="post" class="form_main">
		<div class="input_fields">
			<div class="input_item">
				<label for="latField">LAT : </label> 
	            <input type="text" id="latField" name="latField" value="<%= (lat != null) ? lat : "" %>">
			</div>
			<span>,</span>
			<div class="input_item">
				<label for="lntField">LNT : </label> 
         		<input type="text" id="lntField" name="lntField" value="<%= (lnt != null) ? lnt : "" %>">
			</div>
			<div class="btn_area">
				<button type="button" onclick="getUserLocation()">내 위치 가져오기</button>
				<button type="submit" id="table_loading">근처 WIFI 정보보기</button>
			</div>
		</div>
	</form>

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
			<tr id="loader" class="loading_tr">
				<td colspan="17" class="loading_inner dot">
					<div class="box dot">
						<span></span>
						<span></span>
						<span></span>
					</div>
				</td>
			</tr>
			<%
			if (WifiService.hasData()) {
				if (lat == null && lnt == null) {
			%>
			<tr id="nodata" class="nodata">
				<td colspan="17" style="text-align: center;">위치 정보를 입력한 후에 조회해 주세요.</td>
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
				<td>
					<a href="detail-wifi.jsp?wifiId=<%=wifi.getWifiId()%>">
					<%=wifi.getMainName()%>
					</a>
				</td>
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
			
			<tr class="nodata">
				<td colspan="17" style="text-align: center;">Open API 와이파이 정보를 가져온 후 조회해 주세요.🥲</td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>
	<script>
		document.getElementById('loadWifiLink').addEventListener('click', function() {
		    showLoading();
		});
		document.getElementById('table_loading').addEventListener('click', function() {
		   tblLoading();
		});
	</script>
</body>
</html>