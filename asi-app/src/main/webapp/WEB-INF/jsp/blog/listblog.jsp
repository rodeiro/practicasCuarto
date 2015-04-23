<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<style>
.letragrande{font-size:60%;}

body {
    background-color: aqua;
    text-align:center;
    font-size: 500%;
   
    
}


</style>


<head>
<title>Lista de Blogs</title>
</head>
<body>
	

	<h3>Lista de Blogs</h3>

	<table class="letragrande" style="margin: 0 auto;">
	
		


		<c:forEach var="blog" items="${blogList}" varStatus="status">

			 <tr>
				<td>
					<!-- <form action="blog">
 						<fieldset>
    						<input type="submit" value=${blog.titulo} }>
    						<input type="hidden" name="id" value=${blog.blogId} }>
  						</fieldset>
					</form>	-->
					
					<c:url var= "bloglink"  value="blog">
            			<c:param name="id" value="${blog.blogId}" />
        			</c:url>	
        			<a href='<c:out value="${bloglink}" />'>${blog.titulo}</a> 
				</td> 
			</tr> 
			
							
			

		</c:forEach>  

	</table>
	

</body>
</html>
