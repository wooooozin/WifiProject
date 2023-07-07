<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 보기</title>
<link href="css/main.css" rel="stylesheet">
</head>
<body>
	<h1>북마크 목록</h1>
	
	<div class="top_menu">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> <span>|</span> 
		<a href="show-bookmark.jsp">북마크 보기</a> <span>|</span>
		<a href="manage-bookmark.jsp">북마크 그룹 관리</a>
	</div>
	<table id="manage_table" class="wifi_table">
		<colgroup>
			<col width="10%">
			<col width="auto">
			<col width="auto">
			<col width="auto">
			<col width="15%">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">ID</th>
				<th scope="col">북마크 이름</th>
				<th scope="col">와이파이</th>
				<th scope="col">등록일자</th>
				<th scope="col">비고</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>

</body>
</html>