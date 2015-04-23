<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<style>
body {
    background-color: aqua;
    text-align:center;    
}

h3{
    font-size: 500%;
}

p{
	font-size: 200%;
}


</style>
<head>
<title>Entrada concreta</title>
</head>
<body>
	<h3>${entrada.titulo}</h3>



	<c:choose>
    	  <c:when test="${entrada.tipo=='0'}">
    	  <p>${entrada.texto}</p>
    	  <br />
      	</c:when>

    	  <c:otherwise>
    	   	<p>
    	  		<a href='${entrada.url}'>${entrada.url}</a>
    	 	</p>
    	  
	      <br />
      	</c:otherwise>
	</c:choose>
	
	


</body>
</html>