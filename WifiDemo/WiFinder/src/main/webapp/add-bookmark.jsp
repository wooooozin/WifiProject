<%@page import="db.BookmarkService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 추가</title>
<link href="css/main.css" rel="stylesheet">
</head>
<body>
	<h1>북마크 그룹 추가</h1>
	
	<div class="top_menu">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> <span>|</span> 
		<a href="">북마크 보기</a> <span>|</span>
		<a href="manage-bookmark.jsp">북마크 그룹 관리</a>
	</div>
	
	<form action="add-bookmark.jsp" method="post" class="form_main">			
	<table id="wifi_table" class="wifi_table">
		<tbody>		
			<colgroup>
				<col width="20%">
				<col width="auto">
			</colgroup>
			<tr>
				<th>북마크 이름</th>
				<td>
					<div class="input_item">
						<label for="bookmark_name"></label> 
	           			<input type="text" id="bookmark_name" name="bookmark_name">
					</div>
				</td>
			</tr>
			<tr>
				<th>순서</th>
				<td>
					<div class="input_item">
						<label for="priority"></label> 
	           			<input type="text" id="priority" name="priority">
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;">
					<a href="manage-bookmark.jsp" style="margin-right: 15px;">돌아가기</a>
					<button type="submit">추가</button>
				</td>
			</tr>
			</tbody>			
	</table>	
	</form>
		<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("bookmark_name");
	String priority = request.getParameter("priority");
	if (name != null && priority != null) {
		BookmarkService.insertBookmarkInfo(name, priority);
		%>
	<script>
		alert("북마크가 추가되었습니다.");
		window.location.href = "manage-bookmark.jsp";
	</script>
	<%
	}
	%>
</body>
</html>