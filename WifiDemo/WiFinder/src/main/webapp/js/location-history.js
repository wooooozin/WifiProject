/**
 * 
 */

function confirmDelete(event) {
	if (!confirm("정말로 삭제하시겠습니까?")) {
		event.preventDefault();
	}
}