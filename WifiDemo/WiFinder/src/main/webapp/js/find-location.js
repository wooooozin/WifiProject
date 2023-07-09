/**
 * 
 */

function getUserLocation() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showUserLocation, handleLocationError);
	} else {
		alert("Geolocation is not supported by this browser.");
	}
}

function showUserLocation(position) {
	var latitude = position.coords.latitude;
	var longitude = position.coords.longitude;
	document.getElementById("latField").value = latitude;
	document.getElementById("lntField").value = longitude;
}

function handleLocationError(error) {
	switch (error.code) {
		case error.PERMISSION_DENIED:
			alert("사용자가 위치 정보 공유 권한을 거부했습니다.");
			break;
		case error.POSITION_UNAVAILABLE:
			alert("위치 정보를 사용할 수 없습니다.");
			break;
		case error.TIMEOUT:
			alert("위치 정보를 가져오는 데 시간이 초과되었습니다.");
			break;
		case error.UNKNOWN_ERROR:
			alert("알 수 없는 오류가 발생했습니다.");
			break;
	}
}

function showLoading() {
    let spinner = document.querySelector('#loadingArea');
    spinner.style.display = "block";
}

function hideLoading() {
    let spinner = document.querySelector('#loadingArea');
    spinner.style.display = "none";
}

function tblLoading() {
    const loader = document.querySelector('#loader');
    const nodata = document.querySelector('#nodata');
    loader.style.display = "table-row";
    nodata.style.display = "none";
}


function confirmDelete(event) {
	if (!confirm("정말로 삭제하시겠습니까?")) {
		event.preventDefault();
	}
}

