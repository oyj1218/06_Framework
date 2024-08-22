<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<c:forEach items="${boardTypeList}" var="boardType">
    <c:if test="${boardType.BOARD_CODE == boardCode}" >
        <c:set var="boardName" value="${boardType.BOARD_NAME}"/>
    </c:if>
</c:forEach>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${boardName}</title>

    <link rel="stylesheet" href="/resources/css/board/boardDetail-style.css">
    <link rel="stylesheet" href="/resources/css/board/comment-style.css">
    
    <script src="https://kit.fontawesome.com/f8b69bd1ba.js" crossorigin="anonymous"></script>
</head>
<body>
    <main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        <section class="board-detail">  
            <!-- 제목 -->
            <h1 class="board-title">${board.boardTitle}  <span> - ${boardName}</span>    </h1>

            <!-- 프로필 + 닉네임 + 작성일 + 조회수 -->
            <div class="board-header">
                <div class="board-writer">

                    <!-- 프로필 이미지 -->
                    <c:choose>
                        <c:when test="${empty board.profileImage}">
                            <!-- 프로필 이미지가 없을 경우 기본 이미지 -->
                            <img src="/resources/images/user.png">
                        </c:when>
                        <c:otherwise>
                            <!-- 프로필 이미지가 있을 경우 -->
                            <img src="${board.profileImage}">
                        </c:otherwise>
                    </c:choose>

                    <span>${board.memberNickname}</span>

                    
                    <!-- 좋아요 하트 -->
                    <span class="like-area">

                        <c:choose>
                            <c:when test="${empty likeCheck}">
                                <!-- 좋아요를 누른적이 없거나  -->
                                <i class="fa-regular fa-heart" id="boardLike"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="fa-solid fa-heart" id="boardLike"></i>
                            </c:otherwise>
                        </c:choose>
                        
                        <span>${board.likeCount}</span>
                        
                    </span>

                </div>

                <div class="board-info">
                    <p> <span>작성일</span> ${board.boardCreateDate} </p>     

                    <!-- 수정한 게시글인 경우 -->
                    <c:if test="${!empty board.boardUpdateDate}">
                        <p> <span>마지막 수정일</span>   ${board.boardUpdateDate} </p>   
                    </c:if>

                    <p> <span>조회수</span>  ${board.readCount} </p>                    
                </div>
            </div>
            <!-- ${board} -->
            <!-- 이미지가 있을 경우 -->
            <c:if test="${!empty board.imageList}">

                <c:if test="${board.imageList[0].imageOrder == 0}">
                    
                    <!-- 썸네일 영역(썸네일이 있을 경우) -->
                    <h5>썸네일</h5>
                        <div class="boardImg thumbnail">
                            <img src="${board.imageList[0].imagePath}${board.imageList[0].imageReName}">
                            <a href="${board.imageList[0].imagePath}${board.imageList[0].imageReName}"
                                    download="${board.imageList[0].imageOriginal}">다운로드</a>         
                        </div>
                    </div>

                </c:if>

                <!-- 썸네일을 제외하고 나머지 이미지에 시작 인덱스 번호 -->
                <!-- 썸네일이 있을 경우 -->
                <c:if test="${board.imageList[0].imageOrder == 0}">
                    <c:set var="start" value="0"/>
                </c:if>

                <!-- 썸네일이 없을 경우 -->
                <c:if test="${board.imageList[0].imageOrder != 0}">
                    <c:set var="start" value="1"/>
                </c:if>

                <!-- ${fn:length(boardImageList)} : imageList의 길이 반환 -->


                <!-- 업로드 이미지가 있는 경우 -->
                <c:if test="${fn:length(board.imageList) > start}">

                    <!-- 업로드 이미지 영역 -->
                    <h5>업로드 이미지</h5>
                    <div class="img-box">
                
                        <c:forEach var="i" begin="${start}" end="${fn:length(board.imageList) - 1}">
                            <div class="boardImg">
                                <c:set var="path" value="${board.imageList[i].imagePath}${board.imageList[i].imageReName}"/>
                                <img src="${path}"/>
                                <a href="${path}" download="${board.imageList[i].imageOriginal}"> 다운로드</a>                
                            </div>
                        </c:forEach>
    
                    </div>

                </c:if>


            </c:if>


            <!-- 내용 -->
            <div class="board-content">${board.boardContent}</div>


            <!-- 버튼 영역-->
            <div class="board-btn-area">

                <!-- 로그인한 회원과 게시글 작성자 번호가 같은 경우-->
                <c:if test="${loginMember.memberNo == board.memberNo}">
                    <button id="updateBtn">수정</button>
                    <button id="deleteBtn">삭제</button>
                </c:if>

                <button id="goToListBtn">목록으로</button>
            </div>


        </section>

        <!-- 댓글 include-->
        <jsp:include page="comment.jsp"/>
    </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

    <!-- 
        누가(로그인한 회원 번호) 어떤 게시글(현재 게시글 번호)에 좋아요를 틀릭/취소
        * 로그인 한 회원 번호 얻어오기
        1) jsp 파일 제일 위에 있는 script 태그에 JS + EL 이용해서 전역변수로 선언
        2) Html 요소에 로그인한 회원의 번호를 숨겨 놓고 JS로 얻어오기
        3) ajax로 session에 있는 loginMember의 memberNo 반환
     -->
    

    <script>

        // JSP 해석 우선 순위 : Java/EL/JSTL > html/CSS/JS

        // 작성한 EL 구문이 null일 경우 빈칸으로 출력돼서
        // 변수에 작성된 값이 대입되지 않는 문제가 발생할 수 있음
        // 해결방법 : EL 구문을 '', "" 문자열로 감싸면 해결
        // why? EL 값이 null이어도 ""(빈문자열)로 출력하기 때문에
 

        // 작성한 EL 구문이 null일 경우 빈칸으로 출력되어서 변수에 작성된 값이 대입되지 않는 문제가 발생 가능성 잇음
        // 해결방법 : EL 구분을 '', "" 문자열로 감싸면 해결
        // why? EL 값이 null이어도 ""(빈문자열)로 출력하기 때문에

        // 근데 "" 은 문자열인데 왜 숫자가 나오지?

        // 해석순서 때문
        // JSP 해석 우선 순위 : Java/EL/JSTL > html/CSS/JS
        // el이 제일 먼저 해석되고 그 다음에 js코드가 들어와서 int로 인식한 것

        // 게시글 번호 변수로 선언
        const boardNo = "${board.boardNo}";
        // console.log(boardNo);

        // 로그인한 회원 번호 변수로 선언
        const loginMemberNo = "${loginMember.memberNo}";
        // console.log(loginMemberNo);

        const boardCode = "${boardCode}";
        console.log(boardNo);

    </script>

    <script src="/resources/js/board/boardDetail.js"></script>

    <script src="/resources/js/board/comment.js"></script>



</body>
</html>
