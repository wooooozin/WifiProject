<%@page import="java.io.WriteAbortedException"%>
<%@page import="api.Api"%>
<%@page import="db.WifiService" %>
<%@ page import="java.util.List" %>
<%@page import="model.PublicWifiInfo"%>
<%@page import="model.PublicWifiInfo.Row"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Open API 와이파이 정보 가져오기</title>
<style type="text/css">
.top_elements {
	padding: 15px 0;
	text-align: center;
}
</style>
</head>

<body>
	<div class="top_elements">
		<h1>
			<%
			// api 한번 호출해서 전체 데이터 갯수 저장 및 출력 
			int count = Api.getTotalCount();
			out.write("<h1>");
			out.write(count + "개의 WIFI정보를 정상적으로 저장하였습니다.");
			out.write("</h1>");
			%>
		</h1>
		<div class="top_button">
			<a href="main.jsp">홈으로 돌아가기</a>
		</div>
	</div>
	
	<%
	// 1000단위로 데이터 호출해 DB에 데이터 저장하기
	int totalCount = count;
	int batchSize = 1000;
	for (int start = 1; start <= totalCount; start += batchSize) {
	    int end = Math.min(start + batchSize - 1, totalCount);
	    
		PublicWifiInfo wifiInfo = Api.getWifiInfo(start, end);
		List<Row> infoList = wifiInfo.getTbPublicWifiInfo().getRow();
		WifiService.insertWifiInfo(infoList);
	}
	%>

</body>
</html>