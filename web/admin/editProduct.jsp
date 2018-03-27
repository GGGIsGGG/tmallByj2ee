<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function(){
		
		$("#editForm").submit(function(){
			if(!checkEmpty("name","产品名称"))
				return false;
			if(!checkNumber("orignalPrice","原价格"))
				return false;
			if(!checkNumber("promotePrice","优惠价格"))
				return false;
			if(!checkInt("stock","库存"))
				return false;
			return true;
		});
	});
</script>
<title>修改属性</title>
</head>
<body>
<ol class="breadcrumb">
      <li><a href="admin_category_list">所有分类</a></li>
      <li><a href="admin_product_list?cid=${p.category.id }">${p.category.name}</a></li>
      <li class="active">${p.name}</li>
      <li class="active">编辑产品</li>
   	 	</ol>
<div class="panel panel-warning addDiv">
		<div class="panel-body">
			<form method="post" id="eidtForm" action="admin_product_update" ">
				<table class="addTable">
					<tr>
						<td>产品名称</td>
						<td><input id="name" name="name" value="${p.name }" class="form-control"></td>
					</tr>
					<tr>
						<td>产品小标题</td>
						<td><input id="subTitle" name="subTitle" value="${p.subTitle }" class="form-control"></td>
					</tr>
					<tr>
						<td>原价格</td>
						<td><input id="orignalPrice" name="orignalPrice" value="${p.orignalPrice }" class="form-control"></td>
					</tr>
					<tr>
						<td>优惠价格</td>
						<td><input id="promotePrice" name="promotePrice" value="${p.promotePrice }" class="form-control"></td>
					</tr>
					<tr>
						<td>库存</td>
						<td><input id="stock" name="stock" value="${p.stock }" class="form-control"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="id" value="${p.id }">
							<input type="hidden" name="cid" value="${p.category.id }">
							<button type="submit" class="btn btn-success">提  交</button>
						</td>
					</tr>
				</table>
			</form>
		
		</div>
	
	</div>
	
	</div>
	<%@include file="../include/admin/adminFooter.jsp" %>
</body>
</html>