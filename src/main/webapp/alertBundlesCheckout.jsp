<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Bundle Checkout</title>
 <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="css/mdb.min.css" rel="stylesheet">
  <!-- Your custom styles (optional) -->
  <link href="css/style.min.css" rel="stylesheet">
</head>
<body>
	<main>
	<div class="row">
        <div class="col-sm-10"><h1><strong></strong></h1></div>
        <img src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right"></img>
      </div>
	<div class="container">	
		<div class="row my-5" align="center">
			<p class="h3"><span>Attenzione i seguenti Bundle contengono inserzioni in comune</span></p>
			<p class="h4"><span>Prego rimuovere uno dei seguenti una volta tornato nel carrello</span></p>
		</div>
		<hr>
		<div class="my-5" align="center">
			<c:forEach items="${errorBundles}" var="bundle">
				<p class="h5"><span>${bundle.getTitle()}</span></p>
			</c:forEach>
		</div>
		<hr>
		<div class="my-5"  align="right">
			<a href="cart"><button class="btn btn-brown">Torna al Carrello</button></a>
		</div>
	</div>
	</main>
</body>
</html>
