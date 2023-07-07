<%@page import="db.WifiService"%>
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
<link href="css/location-history.css" rel="stylesheet">
<script src="js/location-history.js"></script>
</head>

<body>
	<h1>위치 히스토리 목록</h1>

	<div class="location_top_buttons">
		<a href="main.jsp">홈</a> <span>|</span> 
		<a href="location-history.jsp">위치 히스토리 목록</a> <span>|</span> 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> <span>|</span> 
		<a href="">북마크 보기</a> <span>|</span>
		<a href="manage-bookmark.jsp">북마크 그룹 관리</a>
	</div>
	
	<table id="history_table" class="history_table">
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
			List<Location> locations = LocationSerivce.getLocalSearchHistory();
			for (Location location : locations) {
			%>

			<tr>
				<td><%=location.getLocationId()%></td>
				<td><%=location.getLatitude()%></td>
				<td><%=location.getLongitude()%></td>
				<td><%=location.getConfirmDate()%></td>
				<td>
                    <form action="location-history.jsp" method="post"
                        class="form_history">
                        <input type="hidden" name="id"
                            value="<%=location.getLocationId()%>">
                        <button type="submit" onclick="confirmDelete(event)">삭제</button>
                    </form>
				</td>
			</tr>
			<%
			}
			}
			String id = request.getParameter("id");
            if (id != null) {
                LocationSerivce.deleteLocationInfo(id);
            %>
            <script>
                alert("삭제되었습니다.");
                window.location.href = "location-history.jsp";
            </script>
            <%
            }
            %>
        </tbody>
	</table>
</body>
</html>