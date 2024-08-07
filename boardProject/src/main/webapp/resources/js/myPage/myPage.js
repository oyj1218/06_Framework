// 내 정보(수정) 페이지

const memberNickname = document.getElementById("memberNickname");
const memberTel = document.getElementById("memberTel");
const updateInfo = document.getElementById("updateInfo");

// 내 정보 수정 form 태그가 존재할 때 (내 정보 페이지)
if(updateInfo != null){

    // 전역변수로 수정 전 닉네임/전화번호를 저장
    initNickname = memberNickname.value;
    initTel = memberTel.value;

    // 닉네임 유효성 검사
    memberNickname.addEventListener("input", ()=>{

        // 변경 전 닉네임과 입력 값이 같을 경우
        if(initNickname == memberNickname.value){
            memberNickname.removeAttribute("style");
            return;
        }
        
        // 정규 표현식으로 유효성 검사
        const regEx = /^[\w\d가-힣]{2,10}$/;

        if(regEx.test(memberNickname.value)){ // 유효
            // 초록 글씨
            memberNickname.style.color = "green";
            // memberNickname.setAttribute("style", "color : green;");
            
        } else { // 무효
            // 빨간 글씨
            memberNickname.style.color = "red";
            // memberNickname.setAttribute("style", "color : red;");
        }

    });

    // 전화번호 유효성 검사
    memberTel.addEventListener("input", ()=>{

        // 변경 전 전화번호와 입력 값이 같을 경우
        if(initTel == memberTel.value){
            memberTel.removeAttribute("style");
            return;
        }

        // 정규 표현식으로 유효성 검사
        const regEx = /^0(1[01679]|2|[3-6][1-5]|70)\d{3,4}\d{4}$/;

        if(regEx.test(memberTel.value)){
            memberTel.style.color = "green";

        } else{
            memberTel.style.color = "red";
        }

    });

    // form 태그 제출 시 유효하지 않은 값이 있으면 제출 X
    updateInfo.addEventListener("submit", e=>{
        
        // 닉네임이 유효하지 않을 경우
        if(memberNickname.style.color == "red"){
            alert("닉네임이 유효하지 않습니다.");
            memberNickname.focus(); // 포커스 이동
            e.preventDefault(); // 기본 이벤트 제거

            return;
        }
    
        // 전화번호가 유효하지 않을 경우
        if(memberTel.style.color == "red"){
            alert("전화번호가 유효하지 않습니다.");
            memberTel.focus(); // 포커스 이동
            e.preventDefault(); // 기본 이벤트 제거

            return;
        }

    });

} 
// 비밀번호 변경 제출 시
const changePwFrm = document.getElementById("changePwFrm");
const currentPw = document.getElementById("currentPw");
const newPw = document.getElementById("newPw");
const newPwConfirm = document.getElementById("newPwConfirm");

// 비밀번호 변경 페이지인 경우
if(changePwFrm != null){
    
    changePwFrm.addEventListener("submit", e=>{
        
        // 현재 비밀번호 미작성 시
        if(currentPw.value.trim() == ""){
            alert("현재 비밀번호를 입력해 주세요.");
            e.preventDefault();
            currentPw.focus();
            currentPw.value = "";
            return;
        }

        // 비밀번호 유효성 검사
        const regEx = /^[a-zA-Z0-9\!\@\#\-\_]{6,20}$/;

        if(!regEx.test(newPw.value)){
            alert("비밀번호가 유효하지 않습니다.");
            e.preventDefault();
            newPw.focus();
            return;
        }

        // 비밀번호 == 비밀번호 확인
        if(newPw.value != newPwConfirm.value){
            alert("비밀번호가 일치하지 않습니다.");
            e.preventDefault();
            newPwConfirm.focus();
            return;
        }
    })
}


// 회원 탈퇴 페이지
// 비밀번호 미작성
// 체크박스 동의 여부
const memberPw = document.getElementById("memberPw");
const agree = document.getElementById("agree");
const secessionFrm = document.getElementById("secessionFrm");

if(secessionFrm != null){
    secessionFrm.addEventListener("submit", e=>{
        
// 비밀번호 미작성
    if(memberPw.value.trim().length === 0){
        alert("비밀번호가 입력되지 않았습니다.");
        memberPw.focus();
        e.preventDefault();
        memberPw.value = "";
        // memberPw.style.color = "red";
        return;
    }
    if(!agree.checked){ // 체크를 안 했을 때
        alert("약관 동의가 체크 되어 있지 않습니다.");
        agree.focus();
        e.preventDefault();
        return;
    }

    
    if(!confirm("정말 정말로 탈퇴할건가요? 진짜? 다시 생각해보세요 제발😥")){ // 취소를 누른 경우
        alert("회원 탈퇴 취소하셨군요! 감사합니다😍💖");
        e.preventDefault();
        return;
    }
    
   
})
}