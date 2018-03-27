<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品</title>
<script type="text/javascript">
	$(function(){
		
		$("#editForm").submit(function(){
			if(!checkEmpty("name","分类名称"))
				return false;
			return true;
		});
	});
</script>
</head>
<body>
<div class="workingArea">
<ol class="breadcrumb">
      <li><a href="admin_category_list">所有分类</a></li>
      <li class="active">${c.name}</li>
      <li class="active">编辑分类</li>
   	 	</ol>
<div class="panel panel-warning addDiv">
		
		<div class="panel-body">
			<form method="post" id="editForm" action="admin_category_update" enctype="multipart/form-data">
				<table class="addTable">
					<tr>
						<td>分类名称</td>
						<td>
							<input id="name" name="name" value="${c.name }" class="form-control">
							<input id="id" name="id" value="${c.id }" type="hidden">
						</td>
					</tr>
					<tr>
						<td>分类图片</td>
						<td>
							<input id="categoryPic" accept="image/*" type="file" name="filepath">
						</td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
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