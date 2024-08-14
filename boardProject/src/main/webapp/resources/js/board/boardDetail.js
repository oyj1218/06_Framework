// 좋아요 버튼이 클릭되었을 때
const boardLike = document.getElementById("boardLike");

boardLike.addEventListener("click", (e)=>{

    if(loginMemberNo == ""){
        alert("로그인 후 이용해주세요");
        return;
    }

    let check; // 기존에 좋아요 X(빈하트) : 0
                //        좋아요 O(꽉찬하트) : 1

    

    // contains("클래스명") : 클래스가 있으면 true, 없으면 false
    if(e.target.classList.contains("fa-regular")){ // 비어있는 하트란 의미 = 좋아요 안 했다
        check = 0;
    } else{ // 꽉찬 하트란 의미 = 좋아요 했다
        check = 1;
    }

    // 너무 보낼 아이들이 많으니깐
    // ajax로 서버에 제출할 파라미터를 모아둔 JS 객체
    const data = {"boardNo" : boardNo,
                "memberNo" : loginMemberNo,
                "check" : check
    };

    // ajax 코드 작성
    fetch("/board/like", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(data)
        // post는 body로 가니깐 @requestBody를 사용해야 한다

    })
    .then(response => response.text()) // 응답객체(포장지)를 필요한 형태로 파싱해서 리턴
    // 0,1 이렇게 나오니깐 json이 아니라 text로 받는다
    .then(result => {  // 파싱된 데이터를 받아서 처리하는 코드 작성
        console.log("result : " + result)

        if(result == -1){ // INSERT, DELETE 실패
            console.log("좋아요 실패")
            return;
        }

        // toggle() : 클래스가 있으면 제거, 없으면 추가
        // 화살표 함수에서는 this를 사용하지 못함
        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        e.target.nextElementSibling.innerText = result;


    })
       
    .catch(e => {
        console.log("예외 발생")
        console.log(e);
    })

    

})