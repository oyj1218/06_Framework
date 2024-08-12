<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>



<%-- map에 저장된 값들을 각각 변수에 저장 --%>
<c:set var="pagination" value="${map.pagination}"/>
<c:set var="boardList" value="${map.boardList}"/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 이름</title>

    <link rel="stylesheet" href="/resources/css/board/boardList-style.css">

</head>
<body>
    <main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        
        <section class="board-list">

            <%-- 방법1 --%>
            <c:forEach var="boardType"  items="${boardTypeList}">
                <c:if test="${boardType.BOARD_CODE==boardCode}">
                    <h1 class="board-name">${boardType.BOARD_NAME}</h1>
                </c:if>
            </c:forEach>

            <%-- 방법2 --%>
            ${boardTypeList[boardCode-1].BOARD_NAME}

            <%-- ${boardCode} : @PathVariable로 request scope에 추가된값 --%>


         

            <div class="list-wrapper">
                <table class="list-table">
                    
                    <thead>
                        <tr>
                            <th>글번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회수</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>

                    <tbody>
                         <!-- 게시글 목록 조회 결과가 비어있다면 -->
                  <!-- 게시글 목록 조회 결과가 있다면 -->
                        <c:choose>
                            <c:when test="${empty boardList}">
                                <tr>
                                    <th colspan="6">게시글이 존재하지 않습니다.</th>
                                </tr>
                            </c:when>
                            <c:otherwise>

                           
                                <c:forEach var="item" items="${boardList}">
                                    <tr>
                                        
                                        <td>${item.boardNo}</td>
                                        <td> 
                                            <c:if test="${!empty item.thumbnail}">
                                                <img class="list-thumbnail" src="${item.thumbnail}">
                                            </c:if>
                                            <a href="/board/${boardCode}/${item.boardNo}?cp=${pagination.currentPage}">${item.boardTitle}[${item.commentCount}]</a>          
                                        </td>
                                        <td>${item.memberNickname}</td>
                                        <td>${item.boardCreateDate}</td>
                                        <td>${item.readCount}</td>
                                        <td>${item.likeCount}</td>
                                    </tr>

                               </c:forEach>    


                            </c:otherwise>

                        </c:choose>


                    </tbody>
                </table>
            </div>


            <div class="btn-area">

            <!-- 로그인 상태일 경우 글쓰기 버튼 노출 -->
                <button id="insertBtn">글쓰기</button>                     

            </div>


            <div class="pagination-area">


                <ul class="pagination">
                
                    <!-- 첫 페이지로 이동 -->
                    <li><a href="/board/${boardCode}?cp=1">&lt;&lt;</a></li>

                    <!-- 이전 목록 마지막 번호로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.prevPage}">&lt;</a></li>

               
                    <!-- 특정 페이지로 이동 -->
                 
                 <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}" step="1">
                        <c:choose>
                            <c:when test="${i == pagination.currentPage}">
                                <li><a class="current">${i}</a></li>

                            </c:when>
                            <c:otherwise>

                                <li><a href="/board/${boardCode}?cp=${i}">${i}</a></li>

                            </c:otherwise>
                        </c:choose>

                 </c:forEach>
                    <!-- 현재 보고있는 페이지 -->
                    <%-- <li><a class="current">${pagination.currentPage}</a></li> --%>
                    
                    <!-- 현재 페이지를 제외한 나머지 -->
                    <%-- <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#">10</a></li> --%>
                    
                    <!-- 다음 목록 시작 번호로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.nextPage}">&gt;</a></li>

                    <!-- 끝 페이지로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.maxPage}">&gt;&gt;</a></li>

                </ul>
            </div>


         <!-- 검색창 -->
            <form action="#" method="get" id="boardSearch">

                <select name="key" id="searchKey">
                    <option value="t">제목</option>
                    <option value="c">내용</option>
                    <option value="tc">제목+내용</tion>
                    <option value="w">작성자</option>
                </select>

                <input type="text" name="query"  id="searchQuery" placeholder="검색어를 입력해주세요.">

                <button>검색</button>
            </form>

        </section>
    </main>
    
    
    <!-- 썸네일 클릭 시 모달창 출력 -->
    <div class="modal">
        <span id="modalClose">&times;</span>
        <img id="modalImage" src="/resources/images/user.png">
    </div>


    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>3