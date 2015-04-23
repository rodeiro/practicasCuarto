<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<style>
body {
    background-color: aqua;
    text-align:center;    
}

h3{
    font-size: 500%;
}

form{
	font-size: 100%;
}


</style>
<head>
<title>Actualizar Entrada</title>
</head>
<body>
	<h3>Actualizar Entrada</h3>

		<form:form action="modificar" method = "POST" modelAttribute="entrada">
			<INPUT TYPE="HIDDEN" NAME="entradaId" VALUE="${entradaId}">
			<INPUT TYPE="HIDDEN" NAME="blog" VALUE="${blog}">
			
			T�tulo:
			<INPUT TYPE = "text" NAME = "titulo">
			<BR><BR><BR>
			
			Selecciona el tipo de entrada: 
			<SELECT NAME = "tipo"> 
				<OPTION SELECTED > Articulo
				<OPTION> Enlace	
			</SELECT>
			<BR><BR><BR>
			
			Texto:
			<INPUT TYPE = "text" NAME = "texto">
			<BR><BR>
			
			URL:
			<INPUT TYPE = "text" NAME = "url">
			<BR><BR>
			
			<input type="submit" value="Add"/>
			<INPUT TYPE="reset" VALUE = "Volver">
		</form:form>		


</body>
</html>