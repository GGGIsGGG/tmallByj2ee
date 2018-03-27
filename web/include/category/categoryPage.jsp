<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模仿天猫官网 ${c.name }</title>


</head>
<body>
<div id= "category">
	<div class="categoryPageDiv">
			<img src="img/category/${c.id }.jpg">
	
		<%@include file="sortBar.jsp" %>
		
		<%@include file="productsByCategory.jsp" %>
		
	
	</div>
</div>
</body>
</html>