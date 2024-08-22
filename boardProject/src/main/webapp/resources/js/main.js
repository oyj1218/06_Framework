const loginFrm = document.getElementById("loginFrm");

const memberEmail = document.querySelector("#memberEmail");
const memberPw = document.querySelector("#memberPw");


if(loginFrm != null){

    //로그인 시도를 할 때 
    loginFrm.addEventListener("submit", e =>{
        //alert("로그인");
    
        if(memberEmail.value.trim() == "" ){
            alert("이메일을 입력해주세요");
            // form 기본 이벤트 제거 
           
            //잘못입력된 값(공백)제거
            //이메일 input 태그에 초점 맞추기
            
            memberEmail.value ="";
            memberEmail.focus();
            //제출 못하게 막기
            e.preventDefault();
            return;
        }
        if(memberPw.value.trim() == "" ){
            alert("비밀번호을 입력해주세요");
            // form 기본 이벤트 제거 
           
            //잘못입력된 값(공백)제거
            //비밀번호 태그에 초점 맞추기
            memberPw.value = "";
            memberPw.focus();
            
            //제출 못하게 막기
            e.preventDefault();
            return;
        }
        
    
    
    });
}

// 비동기로 이메일이 일치하는 회원의 닉네임 조회
function selectNickname(email){
    
    fetch("/selectNickname?email="+ email) 
        // 지정된 주소로 GET방식 비동기 요청(ajax)
        // 전달하고자 하는 파라미터를 주소 뒤 쿼리스트링으로 추가

    .then(response => response.text()) // 요청에 대한 응답 객체(response)를 필요한 형태로 파싱
           //promise객체      //.text() 문자열 형태로 변환
    .then(nickname => {console.log(nickname)}) // 첫 번째 then에서 파싱한 데이터를 이용한 동작 작성

    .catch(e => {console.log(e)}) // 예외 발생시 처리할 내용을 작성

}


// 닉네임이 일치하는 회원의 전화번호 조회
const inputNickname = document.getElementById("inputNickname");
const btn1 = document.getElementById("btn1");
const result1 = document.getElementById("result1");

btn1.addEventListener("click", ()=>{

    // fetch() API를 이용해서 ajax(비동기 통신)
    
    // GET 방식 요청(파라미터를 쿼리스트링으로 추가)
    fetch("/selectMemberTel?nickname=" + inputNickname.value)
    .then( resp => resp.text() )
    // resp : 응답 객체
    // resp.text() : 응답 객체 내용을 문자열로 변환하여 반환

    .then( tel => {
        /* 비동기 요청 후 수행할 코드 */
        result1.innerText = tel; // 조회 결과를 result1에 출력
    })
    // tel : 파싱되어 반환된 값이 저장된 변수

    .catch( err => console.log(err));
    // 에러 발생 시 콘솔에 출력
})

// fetch() API를 이용한 POST 방식 요청

// 이메일을 입력받아 일치하는 회원의 정보를 모두 조회
const inputEmail = document.getElementById("inputEmail");
const btn2 = document.getElementById("btn2");
const result2 = document.getElementById("result2");

btn2.addEventListener("click", ()=>{

    // POST 방식 비동기 요청

    // JSON.stringify() : JS 객체 -> JSON
    // JSON.parse()     : JSON -> JS 객체
    fetch("/selectMember", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify({"email" : inputEmail.value})
    })
    .then(resp => resp.json()) // 응답 객체를 매개변수로 얻어와 파싱
    .then(member => {
        console.log(member);


        // 템플릿 리터럴(Template literals)
        // 내장된 표현식을 허용하는 문자열 리터럴로
        // 표현식 / 문자열 삽입, 여러 줄 문자열, 문자열 형식화 등 다양한 기능을 제공한다
        // ES6부터 ``(백틱) 사용


        // const replyList = document.getElementById("reply-list"); // ul태그
        result2.innerText = "";
        // for(let reply of rList){

        const li1 = document.createElement("li");
        li1.innerText = `회원번호는 ${member.memberNo}`;

        const li2 = document.createElement("li");
        li2.innerText = `이메일은 ${member.memberEmail}`;

        const li3 = document.createElement("li");
        li3.innerText = `닉네임은 ${member.memberNickname}`;

        const li4 = document.createElement("li");
        li4.innerText = `전화번호는 ${member.memberTel}`;

        const li5 = document.createElement("li");
        li5.innerText = `주소는 ${member.memberAddress}`;

        const li6 = document.createElement("li");
        li6.innerText = `가입일은 ${member.enrollDate}`;

        // 이클립스 참고 replyRow.append(replyWriter, replyContent);
        result2.append(li1, li2, li3, li4, li5, li6);


    }) // 파싱한 데이터를 이용해서 비동기 처리 후 동작

    .catch(err => {
        console.log(err)
        result2.innerText = "일치하는 회원이 없습니다.";
    })
})

// 이메일이 일부라도 일치하는 모든 회원 조회
const input = document.getElementById("input");
const btn3 = document.getElementById("btn3");
const result3 = document.getElementById("result3");

btn3.addEventListener("click", ()=>{

    fetch("/selectMemberList", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        // headers : {"Content-Type" : "application/text"},
        body : JSON.stringify({"input" : input.value})
        // body : input.value : 1개만 가는거니깐
    })
    .then(resp => resp.json())
    .then(memberList => {

        console.log(memberList);
        console.log("1:" + memberList);

        result3.innerText = "";

        if(memberList.length == 0){
            result3.innerHtml = "조회 결과 없다";
            return;
        } // null이 아니고 빈칸이기 때문에 list.length로 가져온다
        
        // 이클립스에서 참고 for(let reply of rList){
        console.log("2:"+memberList);

        for(let member of memberList){
            console.log("3:"+member);

        const tr = document.createElement("tr");
        // li1.innerText = `회원번호는 ${member.memberNo}`;

        const td1 = document.createElement("td");
        td1.innerText = member.memberNo;

        const td2 = document.createElement("td");
        td2.innerText = member.memberEmail;

        const td3 = document.createElement("td");
        td3.innerText = member.memberNickname;


        // 이클립스 참고 replyRow.append(replyWriter, replyContent);
        tr.append( td1, td2, td3);
        result3.append(tr);
    }


}) // 파싱한 데이터를 이용해서 비동기 처리 후 동작
    .catch(err => {
        console.log(err)
        
    })
})