<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
  <form class="form-inline" action="/login" method="post">
    <input type="text" class="form-control" placeholder="Enter username">
    <input type="password" class="form-control" placeholder="Enter password">
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
</div>

<%@ include file="../layout/footer.jsp"%>