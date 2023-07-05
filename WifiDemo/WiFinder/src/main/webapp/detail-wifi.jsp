<%@page import="db.WifiService"%>
<%@page import="model.Wifi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 보기</title>
<link href="css/main.css" rel="stylesheet">
</head>
<body>
	<%
	request.setCharacterEncoding("utf-8");
    String wifiId = request.getParameter("wifiId");
	Wifi wifiInfo = WifiService.showDetailWifiInfo(wifiId);
    %>
    
	<h1>와이파이 정보 보기</h1>
	
	<div class="top_menu">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> <span>|</span> 
		<a href="">북마크 보기</a> <span>|</span>
		<a href="manage-bookmark.jsp">북마크 그룹 관리</a>
	</div>
	<div>
		<form action="#" class="form_main">
			<select name="bookmars" id="bookmark">
				 <option value="none" selected>북마크 그룹 이름 선택</option>
			</select>		
			<button type="submit">북마크 추가하기</button>
		</form>
		
	</div>
	
	<table id="wifi_table" class="wifi_table">
		<tbody>
			<colgroup>
				<col width="30%">
				<col width="auto">
			</colgroup>
			<tr>
				<th>거리(Km)</th>
				<td>
				<%=wifiInfo.getDistance()%>
				</td>
			</tr>
			<tr>
				<th>관리번호</th>
				<td>
				<%=wifiInfo.getManagerNumber()%>
				</td>
			</tr>
			<tr>
				<th>자치구</th>
				<td>
				<%=wifiInfo.getWardOffice()%>
				</td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td>
				<%=wifiInfo.getMainName()%>
				</td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td>
				<%=wifiInfo.getAddress1()%>
				</td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td>
				<%=wifiInfo.getAddress2()%>
				</td>
			</tr>
			<tr>
				<th>설치위치(층)</th>
				<td>
				<%=wifiInfo.getInstallationFloor()%>
				</td>
			</tr>
			<tr>
				<th>설치유형</th>
				<td>
				<%=wifiInfo.getInstallationType()%>
				</td>
			</tr>
			<tr>
				<th>설치기관</th>
				<td>
				<%=wifiInfo.getInstallationBy()%>
				</td>
			</tr>
			<tr>
				<th>서비스구분</th>
				<td>
				<%=wifiInfo.getServiceType()%>
				</td>
			</tr>
			<tr>
				<th>망종류</th>
				<td>
				<%=wifiInfo.getNetworkType()%>
				</td>
			</tr>
			<tr>
				<th>설치년도</th>
				<td>
				<%=wifiInfo.getConstructionYear()%>
				</td>
			</tr>
			<tr>
				<th>실내외구분</th>
				<td>
				<%=wifiInfo.getIndoorOutdoor()%>
				</td>
			</tr>
			<tr>
				<th>WIFI접속환경</th>
				<td>
				<%=wifiInfo.getWifiEnvironment()%>
				</td>
			</tr>
			<tr>
				<th>X좌표</th>
				<td>
				<%=wifiInfo.getLatitude()%>
				</td>
			</tr>
			<tr>
				<th>Y좌표</th>
				<td>
				<%=wifiInfo.getLongitude()%>
				</td>
			</tr>
			<tr>
				<th>작업일자</th>
				<td>
				<%=wifiInfo.getWorkDateTime()%>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>