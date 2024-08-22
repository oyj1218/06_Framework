const insertBtn = document.getElementById("insertBtn")

// 글쓰기 버튼 클릭 시
    insertBtn.addEventListener("click", ()=>{
        // JS BOM 객체 중 location

        // location.href = "주소"
        // 해당 주소 요청(GET 방식)

        location.href = '/board2/' + location.pathname.split("/")[2] + '/insert';

        
        // 백틱으로 하면, 이렇게 표시(el표시 아니고 변수임)
        // location.href = `/board2/${location.pathname.split("/")[2]}/insert`

        // /board2/1/insert
    }

)

// 검색창에 검색 기록 남기기
const boardSearch = document.getElementById("boardSearch");
const searchKey = document.getElementById("searchKey");
const boardQuery = document.getElementById("boardQuery");
const options = document.getElementById("#searchKey > option");


(()=>{
    const params = new URL(location.href).searchParams; // t, c, tc, w 중 하나
    const query = params.get("query"); // 검색어

    
    console.log(searchQuery)
    console.log(searchQuery.value)

    if(key!=null){ // 검색을 했을 때
        searchQuery.value = query; // 검색어 화면에 출력

        // option 태그에 하나씩 접근해서 value가 key랑 같으면
        // selected 속성 추가
        for(let option of options){
            if(option.value == key)
                option.selected = true;
        }
    }
})();

// 검색어 입력 없이 제출된 경우
boardSearch.addEventListener("submit", e=>{
    if(searchQuery.value.trim().length == 0) // 검색어 미입력 시
        e.preventDefault(); // form 기본 이벤트 제거

        location.href = location.pathname; // 해당 게시판 1페이지로 이동

        // location.pathname : 쿼리스트링을 제외한 실제 주소
        
})