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
    <title>
    </title>

    <link rel="stylesheet" href="/resources/css/board/boardList-style.css">

</head>
${boardTypeList}
${param.cp}
<body>
    <main>
        <jsp:include page="/WEB-INF/views/common/header.jsp"/>

        <%-- 검색을 진행한 경우 파라미터(key, query)를 쿼리스트링 형태로 저장한 변수를 선언 --%>
        <c:if test="{!empty param.key}">
            <c:set var="qs" value="&key=${param.key}&query=${param.query}"/>
        </c:if>
        
        <section class="board-list">


            <h1 class="board-name">

                <c:if test="${!empty param.key}">
                    <h3 class="searchResult">"${param.query}" 검색 결과</h3>
                </c:if>
                <%-- <c:forEach var="list" items="${boardTypeList}">

                    <c:if test="${list.BOARD_CODE==boardCode}">
                        ${list.BOARD_NAME}
                    </c:if>

                 </c:forEach> --%>
                <%-- 두 가지 모두 가능하다. --%>
                ${boardTypeList[boardCode-1].BOARD_NAME}

            </h1>
            

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

                <c:if test="${!empty loginMember}">
                    <button id="insertBtn">글쓰기</button>                     
                </c:if>

            </div>


            <div class="pagination-area">


                <ul class="pagination">
                
                    <!-- 첫 페이지로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.startPage}${qs}">&lt;&lt;</a></li>
                    
                    <!-- 이전 목록 마지막 번호로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.prevPage}${qs}">&lt;</a></li>

               
                    <!-- 특정 페이지로 이동 -->

                    <!-- 현재 보고있는 페이지 -->
<%-- Pagination [currentPage=2, listCount=328, limit=10, pageSize=10, maxPage=33, startPage=1, endPage=10, prevPage=1, nextPage=11]
328
Pagination [currentPage=12, listCount=328, limit=10, pageSize=10, maxPage=33, startPage=11, endPage=20, prevPage=10, nextPage=21] --%>
                    <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}">
                        <c:choose>
                            <c:when test="${empty param.cp && i==1}">
                                <li><a class="current">${pagination.currentPage}</a></li> 
                            </c:when>
                            <c:when test="${param.cp==i}">
                                <li><a class="current">${pagination.currentPage}</a></li>    
                            </c:when>
                            <c:otherwise>
                                <li><a href="/board/${boardCode}?cp=${i}${qs}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    
                    <!-- 현재 페이지를 제외한 나머지 -->
                    <%-- <li><a href="/board/1?cp=2">${pagination.currentPage+1}</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">6</a></li>
                    <li><a href="#">7</a></li>
                    <li><a href="#">8</a></li>
                    <li><a href="#">9</a></li>
                    <li><a href="#">10</a></li> --%>
                    
                    <!-- 다음 목록 시작 번호로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.nextPage}${qs}">&gt;</a></li>

                    <!-- 끝 페이지로 이동 -->
                    <li><a href="/board/${boardCode}?cp=${pagination.maxPage}${qs}">&gt;&gt;</a></li>

                </ul>
            </div>


         <!-- 검색창 -->
            <form action="${boardCode}" method="get" id="boardSearch">

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
    <script src="/resources/js/board/boardList.js"></script>
</body>
</html>