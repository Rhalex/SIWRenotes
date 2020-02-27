<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Descrizone Bundle</title>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="css/mdb.min.css" rel="stylesheet">
  <!-- Your custom styles (optional) -->
  <link href="css/style.min.css" rel="stylesheet">
	<!-- SCRIPTS -->
  <!-- JQuery -->
  <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="js/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="js/mdb.min.js"></script>
  <!-- Initializations -->
  
  <script src="js/javaScriptUtility.js"></script>

  
</head>

<body onload="calculateParameters()">
<!-- Header content -->
<jsp:include page="navbar.jsp"></jsp:include>
	<br>
	<main>
	<div class="container">
		<section class="mt-5 wow fadeIn">
		<br>
			<p class="h3 " align="center"><span>${bundle.getTitle()}</span></p>
        <!--Grid row-->
        <c:forEach items="${ads}" var="ad">
	        <div class="row">
	          <!--Grid column-->
				   <div class="col-md-4 mb-3 my-4">
				    <a href="GetAdInfo?adIdByList=${ad.getId()}&viewButton=false"><img src="${ad.getPreview().getImage()}" class="img-fluid z-depth-1-half my-2"  alt="" width="200" height="200"></img></a>
				  </div>
				  <div class="col-md-4 mb-3 my-4">
					<p class="h3"><span>${ad.getTitle()}</span></p>
					<p><span>Materia :</span> ${ad.getSubject()}</p>
					<p><span>Universita' :</span>${ad.getUniversity()}</p>
					<p><span>Corso di Laurea :</span> ${ad.getDegreeCourse()}</p>
					<font color="b00000"><p class=" h4"> ${ad.getPrice()} <a class="fas fa-euro-sign"></a></p></font>
				  </div>
	     		 <div class="form-row">
	     		 	<div class="col-4 my-5 mx-5"> 
				      <label>${ad.getValue()} <a class="fas fa-star text-warning"></a></label>
					</div>		
				</div>
			  </div>
				<hr>
			</c:forEach>
			
			<div align="right">
			<figcaption>Created by <a href="ViewSummaryProfile?u=${bundle.getUser().getUsername()}">${bundle.getUser().getUsername()}</a> </figcaption>
			<font color="b00000"><p class=" h4">Prezzo Bundle: ${bundle.getPrice()} <a class="fas fa-euro-sign"></a></p></font>
			<br>
			 <c:if test="${viewButton == null}">
			   	  <form method="post" action="ManageCart">
			   	  	<textarea name="bundleIdAdd" hidden>${bundle.getBundle_id()}</textarea>
			   	  	<input type="submit" class="btn btn-success btn-md text-white font-weight-bold" value="AGGIUNGI AL CARRELLO" />
			   	  </form>
		   	  </c:if>
			</div>
			<br>
			<br>
		</div>
		
		</section>
	</div>
  </main>
  <jsp:include page="footer.jsp"></jsp:include>

  
</body>
</html>
