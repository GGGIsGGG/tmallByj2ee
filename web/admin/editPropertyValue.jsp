<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑产品属性值</title>
<script type="text/javascript">
$(function(){
	$("input.pvValue").keyup(function(){
		var value = $(this).val();
		var page = "admin_product_updatePropertyValue";
		var pvid = $(this).attr("pvid");
		var parentSpan = $(this).parent("span");
		parentSpan.css("border", "1px solid blue");
		$.post(
			page,
			{"value":value,"pvid":pvid},
			function(result){
				if("success"==result)
					parentSpan.css("border","1px solid lightgreen");
				else
					parentSpan.css("border","1px solid red");
			}
		
		);
		
		
	});
	
	
});
</script>
</head>
<body>
<div class="workingArea">
		<ol class="breadcrumb">
      <li><a href="admin_category_list">所有分类</a></li>
      <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
      <li class="active">${p.name}</li>
      <li class="active">编辑产品属性</li>
   	 	</ol>
		<div class="editPVDiv">
			<c:forEach items="${pvs }" var="pv">
				<div class="eachPV">
					<span class="pvName">${pv.property.name }</span>
					<span class="pvValue"><input class="pvValue" pvid="${pv.id }" value="${pv.value }"></span>
				</div>
			</c:forEach>
			<div style="clear:both"></div>
		</div>
	</div>
	<%@include file="../include/admin/adminFooter.jsp" %>
</body>
</html>