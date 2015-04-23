<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<style>
.letragrande{font-size:60%;}

body {
    background-color: aqua;
    text-align:center;
    font-size: 300%;
    
}


button{
	position: fixed;
	top: 20px;
	right:20px;
}


img{
	height: 60px; 
	position:fixed;
	top: 12px;
	left: 12px;
}



div{
	font-weight: bold;
	background-color: skyblue;
	height: 70px;
	font-size: 110%;


}


form{
height: 10px;
width: 10px;
position:relative;
top: 0px;
left: 1070px;

}

</style>


<head>
<title>Lista de entradas</title>
</head>
<body>


	<div>
			<img src="https://pbs.twimg.com/profile_images/378800000229479563/902f62671b936847b89198445a2de1f5_400x400.png" alt="FicBlogs" />

		FICBLOGS
 		<form action="nuevaEntrada" method="get">
 			<!--  <button name="id" type="submit" value= ${id}>Nueva</button>  -->
 			 <button  onclick=<input type="long" name="blogId" value="${id}"/>Nueva</button>
		</form>
	</div>

<!-- 
					<form action="nuevaEntrada">
 						<fieldset>
    						<input type="submit" value="+" }>
    						<input type="hidden" name="blogId" value=${id}>
  						</fieldset>
					</form>
 -->
	<h3>Lista de Entradas</h3>

	<table class="letragrande" style="margin: 0 auto;">
	
	

		<c:forEach var="entrada" items="${listE}" varStatus="status">

			<tr>
				
				<td>
					<!-- 
					<form action="entrada">
 						<fieldset>
    						<input type="submit" value=${entrada.titulo} }>
    						<input type="hidden" name="entradaId" value=${entrada.entradaId}>
  						</fieldset>
					</form>  -->
				
					<c:url var= "entradalink"  value="entrada">
            			<c:param name="entradaId" value="${entrada.entradaId}" />
        			</c:url>	
        			<a href='<c:out value="${entradalink}" />'>${entrada.titulo}</a> 
				</td>
				<td>
					<c:url var= "borrarlink"  value="borrarEntrada">
            			<c:param name="entradaId" value="${entrada.entradaId}" />
        			</c:url>	
        			<a href='<c:out value="${borrarlink}" />'>-- Borrar</a>
				</td>
				<td>
					<c:url var= "modificarlink"  value="modificarEntrada">
            			<c:param name="entradaId" value="${entrada.entradaId}" />
            			<c:param name="blog" value="${entrada.blog}" />
        			</c:url>	
        			<a href='<c:out value="${modificarlink}" />'>-- Modificar</a>
				</td>

			</tr>

		</c:forEach>  

	</table>


				

</body>
</html>