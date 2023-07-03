<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 관리</title>
<link href="css/main.css" rel="stylesheet">
</head>

<body>
	<h1>북마크 그룹 관리</h1>
	
	<div class="top_menu">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> <span>|</span> 
		<a href="">북마크 보기</a> <span>|</span>
		<a href="manage-bookmark.jsp">북마크 그룹 관리</a>
	</div>

	<form action="" class="form-manage">
		<div>
			<button type="button" onclick="window.location.href='add-bookmark.jsp'">북마크 그룹 이름 추가</button>
		</div>
	</form>

	<table id="manage_table" class="wifi_table">
		<colgroup>
			<col width="10%">
			<col width="auto">
			<col width="10%">
			<col width="15%">
			<col width="15%">
			<col width="auto">
		</colgroup>
		<thead>
			<tr>
				<th scope="col">ID</th>
				<th scope="col">북마크 이름</th>
				<th scope="col">순서</th>
				<th scope="col">등록일자</th>
				<th scope="col">수정일자</th>
				<th scope="col">비고</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="6" style="text-align: center;">
				등록된 북마크가 없어요 😢
				</td>
			</tr>
		</tbody>
	</table>

</body>
</html>