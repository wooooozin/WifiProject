<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style type="text/css">
.input_fields {
	margin-top: 15px;
	margin-bottom: 15px;
}

.input_fields .input_item {
	display: inline-block;
}

.input_fields .input_item+.input_item {
	margin-left: 6px;
}

#wifi_table {
	font-family: Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#wifi_table td, #customers th {
	border: 1px solid #ddd;
	padding: 8px;
}

#wifi_table tr:nth-child(even) {
	background-color: #f2f2f2;
}

#wifi_table tr:hover {
	background-color: #ddd;
}

#wifi_table th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	background-color: #04AA6D;
	color: white;
	border: 1px solid white;
}

#wifi_table td {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: left;
	color: black;
	border: 1px solid lightgray;
}
</style>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<div class="top_buttons">
		<a href="main.jsp">홈</a> | <a href="#none">위치 히스토리 목록</a> | 
		<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
	</div>
	<div class="input_fields">
		<div class="input_item">
			<label for="latFiedl">LAT: </label> <input type="text" id="latFiedl">
		</div>
		<span>,</span>
		<div class="input_item">
			<label for="lntFiedl">LNT: </label> <input type="text" id="lntFiedl">
		</div>
		<button type="button">내 위치 가져오기</button>
		<button type="button">근처 WIFI 정보보기</button>
	</div>

	<table id="wifi_table">
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
			<tr>
				<td colspan="17" style="text-align: center;">위치 정보를 입력한 후에 조회해
					주세요.</td>
			</tr>
		</tbody>
	</table>

</body>
</html>