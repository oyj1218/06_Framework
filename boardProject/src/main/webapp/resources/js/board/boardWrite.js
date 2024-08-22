// 미리보기 관련 요소 모두 얻어오기

// img 5개
const preview = document.getElementsByClassName("preview")

// file 5개
const inputImage = document.getElementsByClassName("inputImage")

// x 버튼 5개
const deleteImage = document.getElementsByClassName("delete-image")

// -> 위에서 얻어온 요소들의 개수가 같음 == 인덱스가 일치함
for(let i=0; i<inputImage.length; i++){
    // 꼭 inputImage 아니어도 괜찮, 어차피 다 같으니깐
    // for문 사용한 이유 -> 배열이라 inputImage.add 이렇게 못 씀(하나하나 요소에 직접 추가해줘야 해서)

    // 파일이 선택되거나, 선택 후 취소 되었을 때
    inputImage[i].addEventListener("change", e=>{
        
        const file = e.target.files[0]; // 선택된 파일의 데이터
        
        if(file != undefined){ // 파일이 선택 되었을 때
            
            const reader = new FileReader(); // 파일을 읽는 객체

            reader.readAsDataURL(file); // 지정된 파일을 읽은 후 result에 URL 형식으로 저장

            reader.onload = function(e){// reader가 파일을 다 읽은 후 수행
                // e.target == reader
                // e.target.result == 읽어들인 이미지의 URL 포함  
                // preview[i] == 파일이 선택된 input 태그와 인접한 preview 이미지 태그  
                preview[i].setAttribute("src", e.target.result);

            }
            

        } else{ // 파일이 선택이 되지 않았을 때 (취소)
            preview[i].removeAttribute("src");


        }


    })

    // 미리보기 삭제 버튼(x)이 클릭 되었을 때
    deleteImage[i].addEventListener("click", ()=>{

        // 미리보기 이미지가 있을 경우
        if(preview[i].getAttribute("src") != ""){
            
            // 미리보기 삭제
            alert("사진이 있습니다. 이 사진을 정말 취소하시겠습니까?")

            preview[i].removeAttribute("src");

            // input type="file" 태그의 value 삭제
            // ** input type="file"의 value는 빈칸만 대입 가능
            inputImage[i].value = "";

        }
    })

}

// 제목, 내용입력
const boardWriteFrm = document.getElementById("boardWriteFrm");
const boardTitle = document.querySelector("[name='boardTitle']");
const boardContent = document.querySelector("[name='boardContent']");


boardWriteFrm.addEventListener("submit", e=>{
    
    if(boardTitle.value.trim().length == ""){
        alert("제목이 없습니다. 입력해주세요");
        boardTitle.focus();
        boardTitle.value= "";
        e.preventDefault();

        return;
    }

    if(boardContent.value.trim().length == ""){
        alert("내용이 없습니다. 입력해주세요");
        boardContent.focus();
        boardContent.value= "";
        e.preventDefault();

        return;
    }

})
