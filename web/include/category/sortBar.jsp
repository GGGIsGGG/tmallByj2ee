<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script>
$(function(){
	$("input.sortBarPrice").keyup(function(){
		var num = $(this).val();
		if(num.length== 0){
			$("div.productUnit").show();
		}
		
		num = parseInt(num);
		if(isNaN(num))
			num=1;
		if(num<=0)
			num = 1;
		$(this).val(num);
		
		var begin = $("input.beginPrice").val();
		var end = $("input.endPrice").val();
		if(!isNaN(begin) && !isNaN(end)){
			console.log(begin);
			console.log(end);
			$("div.productUnit").hide();
			$("div.productUnit").each(function(){
				var price = $(this).attr("price");
				price = new Number(price);
				
				if(price<= end && price>=begin)
					$(this).show();
					
			});
		}
	});
	
	
});
</script>

<div class="categorySortBar">
	
	<table class="categorySortBarTabel categorySortBarTable">
		<tr>
			<td <c:if test="${'all'==param.sort || empty param.sort }">class="grayCloum"</c:if>>
				<a href="?cid=${c.id}&sort=all ">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
			</td>
			<td <c:if test="${'price'==param.sort }">class="grayCloum"</c:if>>
				<a href="?cid=${c.id}&sort=price ">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
			</td>
			<td <c:if test="${'date'==param.sort}">class="grayCloum"</c:if>>
				<a href="?cid=${c.id}&sort=date ">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
			</td>
			<td <c:if test="${'saleCount'==param.sort}">class="grayCloum"</c:if>>
				<a href="?cid=${c.id}&sort=saleCount ">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
			</td>
			<td <c:if test="${'price'==param.sort }">class="grayCloum"</c:if>>
				<a href="?cid=${c.id}&sort=price ">价格<span class="glyphicon glyphicon-resize-vertical"></span></a>
			</td>
		</tr>
	</table>
	
	<table class="categorySortBarTable">
		<tr>
			<td><input class="sortBarPrice beginPrice" placeholder="请输入"></td>
			<td class="grayColumn priceMiddleColumn">-</td> 
			<td><input class="sortBarPrice endPrice" placeholder="请输入"></td>
		</tr>
	</table>


</div>