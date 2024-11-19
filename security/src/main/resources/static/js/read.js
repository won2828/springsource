// 전체 댓글 내용 표시
const replyList = document.querySelector(".reply-list");

// 날짜 처리 함수
const formatDateTime = (str) => {
  const date = new Date(str);
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 페이지가 로드되면 현재 bno의 댓글 가져오기
const replyLoaded = () => {
  fetch(`/replies/board/${bno}`)
    .then((response) => {
      if (!response.ok) throw new Error("에러");

      return response.json();
    })
    .then((data) => {
      console.log(data);

      // 전체 댓글 수 표시
      document.querySelector(".d-inline-block").innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border bottom reply-row" data-rno="${reply.rno}">`;
        result += ` <div class="p-3"><img src="/img/default.png" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px"/></div>`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<span>${reply.replyer}</span>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDateTime(
          reply.regDate
        )}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center"><div class="mb-2">`;
        result += `<button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });

      replyList.innerHTML = result;
    })
    .catch((error) => {
      console.error("댓글 로딩 에러:", error);
    });
};

replyLoaded();

// 댓글 작성
const replyForm = document.querySelector("#replyForm");
replyForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const replyer = replyForm.querySelector("#replyer");
  const text = replyForm.querySelector("#text");
  const rno = replyForm.querySelector("#rno");

  // 자바스크립트 객체 생성
  const reply = {
    text: text.value,
    replyer: replyer.value,
    bno: bno,
    rno: rno.value,
  };

  if (!rno.value) {
    // 새로운 댓글을 추가할 경우
    fetch(`/replies/new`, {
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(reply),
      method: "post",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("댓글 삽입 에러 발생");
        }
        return response.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          // 댓글 폼에 남아있는 내용 제거하기
          replyer.value = "";
          text.value = "";
          alert(data + " 번 댓글이 등록되었습니다.");
          replyLoaded();
        }
      })
      .catch((error) => {
        console.error("댓글 삽입 중 에러 발생:", error);
      });
  } else {
    // 댓글 수정 부분
    fetch(`/replies/${rno.value}`, {
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(reply),
      method: "put",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("댓글 수정 에러 발생");
        }
        return response.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          replyer.value = "";
          text.value = "";
          rno.value = "";
          alert(data + " 번 댓글이 수정되었습니다.");
          replyLoaded();
        }
      })
      .catch((error) => {
        console.error("댓글 수정 중 에러 발생:", error);
      });
  }
});

// 댓글 수정
replyList.addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".reply-row").dataset.rno;

  if (btn.classList.contains("btn-outline-danger")) {
    // 삭제 버튼 클릭 시
    if (confirm("정말 삭제하시겠습니까?")) {
      fetch(`/replies/${rno}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("댓글 삭제 에러 발생");
          }
          return response.text();
        })
        .then((data) => {
          console.log(data);
          alert(data + " 번 댓글이 삭제되었습니다.");
          replyLoaded();
        })
        .catch((error) => {
          console.error("댓글 삭제 중 에러 발생:", error);
        });
    }
  } else if (btn.classList.contains("btn-outline-success")) {
    // 수정 버튼 클릭 시
    fetch(`/replies/${rno}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        // 해당 댓글을 replyForm 안에 보여주기
        replyForm.querySelector("#rno").value = data.rno;
        replyForm.querySelector("#replyer").value = data.replyer;
        replyForm.querySelector("#text").value = data.text;
      })
      .catch((error) => {
        console.error("댓글 로딩 에러 발생:", error);
      });
  }
});
