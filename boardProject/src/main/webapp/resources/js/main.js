const loginFrm = document.getElementById("loginFrm");

const memberEmail = document.querySelector("#loginFrm input[name='memberEmail']");
const memberPw = document.querySelector("#loginFrm input[name='memberPw']");

if(loginFrm != null){
    // 로그인 시도를 할 때
    loginFrm.addEventListener("submit", e => {
        // alert("로그인");
    
        // form 태그 기본 이벤트 제거
        // e.preventDefault();
  
        // 이메일이 입력되지 않은 경우
        if(memberEmail.value.trim().length == 0){
            alert("이메일을 입력해 주세요.");

            // 잘못 입력된 값(공백) 제거
            memberEmail.value = "";

            // 이메일 input 태그에 초점 맞춤
            memberEmail.focus();

            // 제출 못 하게 하기
            e.preventDefault();
            return;
        }
        
        // 비밀번호가 입력되지 않은 경우
        if(memberPw.value.trim().length == 0){
            alert("비밀번호를 입력해 주세요.");

            // 잘못 입력된 값(공백) 제거
            memberPw.value = "";

            // 이메일 input 태그에 초점 맞춤
            memberPw.focus();

            // 제출 못 하게 하기
            e.preventDefault();
            return;
        }

    })
}