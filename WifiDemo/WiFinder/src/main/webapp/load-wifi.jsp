<%@page import="api.Api"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.top_elements {
	padding: 15px 0;
	text-align: center;
}
/* .top_elements .top_button {display: block;margin-top: 30px;} */
</style>
</head>

<body>
	<div class="top_elements">
		<h1>
			<%
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
	int totalCount = count;
	int batchSize = 1000;
	for (int start = 1; start <= totalCount; start += batchSize) {
	    int end = Math.min(start + batchSize - 1, totalCount);
	    
	    System.out.println(start + ", " + end);
	}
	%>

</body>
</html>