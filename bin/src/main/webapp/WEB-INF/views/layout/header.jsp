<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>CHR PROJECT</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h2>ITLOG</h2>
		<p>사이트 버튼 클릭해주시기 바랍니다.</p>
		<ul class="nav flex-column">

			<c:choose>
				<c:when test="${empty sessionScope.principal}">
					<!-- empty : null이거나 공백이거나 -->
					<li class="nav-item"><a class="nav-link" href="/loginForm">로그인</a>
					</li>

					<li class="nav-item"><a class="nav-link" href="/joinForm">회원가입</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a>
					</li>

					<li class="nav-item"><a class="nav-link" href="/user/${sessionScope.principal.id}">회원정보</a>
					</li>
					
					<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a>
					</li>
				</c:otherwise>
			</c:choose>

		</ul>
	</div>

</body>
</html>