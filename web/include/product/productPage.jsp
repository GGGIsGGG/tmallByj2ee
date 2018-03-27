<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模仿天猫官网 ${p.name }</title>


</head>
<body>
<div class="categoryPictureInProductPageDiv">
	<img class="categoryPictureInProductPage" src="img/category/${p.category.id }.jpg">
</div>

<div class="productPageDiv">

	<%@include file="imgAndInfo.jsp" %>
	
	<%@include file="productReview.jsp" %>
	
	<%@include file="productDetail.jsp" %>
	

</div>
</body>
</html>