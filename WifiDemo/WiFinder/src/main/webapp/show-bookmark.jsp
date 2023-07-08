<%@page import="db.BookmarkListService"%>
<%@page import="model.BookmarkList"%>
<%@page import="java.util.List" %>

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
		<%
			if (!BookmarkListService.hasData()) {
			%>
			<tr>
				<td colspan="6" style="text-align: center;">등록된 북마크가 없어요 😢</td>
			</tr>
			<%
			} else {
			List<BookmarkList> bookmarkLists = BookmarkListService.showBookmarkLists();
			for (BookmarkList bookmarkList : bookmarkLists) {
				String bookmarkListId = bookmarkList.getBookmarkListId();
				String bookmarkName = bookmarkList.getBookmarkName();
				String wifiId = bookmarkList.getWifiId();
				String wifiName = bookmarkList.getWifiMainName();
				String regitDate = bookmarkList.getRegitDate();
			%>
			<tr>
				<td style="text-align: center"><%=bookmarkListId%></td>
				<td style="text-align: center"><%=bookmarkName%></td>
				<td style="text-align: center">
					<a href="detail-wifi.jsp?wifiId=<%=wifiId%>">
					<%=wifiName%>
					</a>
				</td>
				<td style="text-align: center"><%=regitDate%></td>
				<td style="text-align: center">
				<a href="delete-bookmarklist.jsp?id=<%=bookmarkListId%>
				&bookmark_name=<%=bookmarkName%>
				&wifi_name=<%=wifiName%>
				&regit_date=<%=regitDate%>">삭제</a>
				</td>
			</tr>
			<%
			}
			}
			%>
			
		</tbody>
	</table>

</body>
</html>