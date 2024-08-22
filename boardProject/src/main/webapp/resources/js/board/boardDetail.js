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

// 게시글 수정 버튼 클릭 시
const updateBtn = document.getElementById("updateBtn");

if(updateBtn != null){
    updateBtn.addEventListener("click", ()=>{
        location.href = location.pathname.replace("board", "board2") + '/update' + location.search;
    })
    
}

// 게시글 삭제 버튼 클릭 시
const deleteBtn = document.getElementById("deleteBtn");

if(deleteBtn != null){
    deleteBtn.addEventListener("click", ()=>{
        
        // "정말 삭제 하시겠습니까?"
        if(confirm("정말 삭제 하실건가요?")){
            // alert, prompt, confirm 3가지가 있다
        
        // yes -> /board2/1/1588/delete (Get)

        // 삭제 서비스 호출 성공 시 -> 해당 게시판 목록 / "게시글이 삭제되었습니다"

        // 삭제 서비스 호출 실패 시 -> 게시글 상세조회 페이지 "게시글 삭제 실패"
        location.href = location.pathname.replace("board", "board2") + '/delete' + location.search;
        }
    }

)}

// 목록으로
const goToListBtn = document.getElementById("goToListBtn")

goToListBtn.addEventListener("click", ()=>{  

    // 1) 
    location.href = "/board/" + boardCode + location.search
    // location.search : 쿼리스트링만 반환

    // 2) 
    // const pathname = location.pathname;
    // pathname : "/board/1/3116"
   
    // 이동할 주소 저장
    // let url = "/board/" + boardCode;
    // board
    // http://localhost/board/1



    /*const params = new URL(location.href).searchParams;

            let cp;
            
            if(params.get("cp") != ""){ // 쿼리스트링에 cp가 있을 경우
               cp = "?cp=" + params.get("cp");
            } else{
                cp = "?cp=1";
            }
            // http://localhost/board/1/3116?cp=1

            // 조립
            url += cp;

            // 검색 key, query가 존재하는 경우 url 추가
            if(params.get("key") != null){
                // http://localhost/board/1?key=t&query=~
                const key = "&key=" + params.get("key");
                const query = "&query=" + params.get("query");

                url += key + query;
            }
            location.href = url;
            */
})

