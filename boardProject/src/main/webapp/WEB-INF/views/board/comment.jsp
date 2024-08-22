<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="commentArea">
    <!-- 댓글 목록 -->
    <div class="comment-list-area">
        
        <ul id="commentList">
            <!-- ${board.commentList}--> <!-- 확인용 -->

            <c:forEach items="${board.commentList}" var="comment">
            ${comment}
            
            
                <!-- 부모 댓글 -->
                <li class="comment-row 
                <c:if test='${comment.parentNo !=0}'>child-comment</c:if>">
                <!-- 만약 부모댓글이 있다면 자식댓글이 보이게 한다 -->
                    <p class="comment-writer">

                        <!-- 프로필 이미지 -->
                        <!-- 프로필 이미지가 없으면-->
                        <c:if test="${empty comment.profileImage}">
                            <img src="/resources/images/user.png">
                        </c:if>
                        
                        <!-- 프로필 이미지가 있으면-->
                        <c:if test="${!empty comment.profileImage}">
                            <img src="${comment.profileImage}">
                        </c:if>

                        

                        <!-- 닉네임 -->
                        <span>${comment.memberNickname}</span>
                        
                        <!-- 작성일 -->
                        <span class="comment-date">${comment.commentCreateDate}</span>
                    </p>
                    
                    <!-- 댓글 내용 -->
                    <p class="comment-content">${comment.commentContent}</p>


                        <!-- 버튼 영역 -->
                        <div class="comment-btn-area">
                        <c:if test="${!empty loginMember.memberNo}">
                            <button onclick="showInsertComment(${comment.commentNo}, this)">답글</button>
                        </c:if>
                        <!--
                        <button onclick="showInsertComment(${comment.commentNo}, this)">답글</button>   
                        -->
                        <!-- 로그인 회원과 댓글 작성자가 같을 때는 -->  
                        <c:if test="${loginMember.memberNo == comment.memberNo}">
                            <button onclick="showUpdateComment(${comment.commentNo}, this)">수정</button>
                            <button onclick="deleteComment(${comment.commentNo})">삭제</button>
                        </c:if>
                    </div>
                </li>

            </c:forEach>
            
        </ul>
    </div>
    

    <!-- 댓글 작성 부분 -->
    <div class="comment-write-area">
        <textarea id="commentContent"></textarea>
        <button id="addComment">
            댓글<br>
            등록
        </button>
 
    </div>

</div>
