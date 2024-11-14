const actionForm = document.querySelector("#actionForm");

// Remove 클릭 시
document.querySelector(".btn-danger").addEventListener("click", () => {
  // actionFrom action 수정
  if (!confirm("삭제하시겠습니까?")) {
    return;
  }
  actionForm.action = "/guestbook/remove";
  actionForm.submit();
});

// List 클릭 시
document.querySelector(".btn-info").addEventListener("click", () => {
  // actionForm method 수정(get)
  actionForm.method = "get";
  // gno 삭제
  actionForm.querySelector("[name='gno']").remove();
  actionForm.submit();
});
