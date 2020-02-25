<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Descrizone Inserzione</title>
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
</head>

<body>
<!-- Header content -->
<header>
<!-- NavBar -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark scrolling-navbar">
    <div class="container">

      <!-- Brand -->
      <form action="ViewAdList" method="post" class="mx-5">
        	<input type="image" src= "bootstrap2/resources/icon1_white.png" width="60" height="60" class="pull-right">
      </form>

      <!-- Collapse -->
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
        aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <!-- Links -->
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <!-- Left -->
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
			<form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-auto my-auto my-md-auto">
     			<div class="input-group">
      				 <input type="text" class="form-control" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" name="titolo" form="searchForm">
      			</div>
			</form>
          </li>
        </ul>

        <!-- Right -->
        <ul class="navbar-nav nav-flex-icons">

          <li class="nav-item">
            <a class="nav-link" href="cart">
              <i class="fas fa-cart-arrow-down"></i>
            </a>
          </li>
		  <li class="nav-item">
            <a class="nav-link" href="profile.jsp">
              <i class="fas fa-user"></i>
            </a>
          </li>
          
        </ul>

      </div>

    </div>
  </nav>
  <!-- Navbar -->

</header>
	
	
	<main>
    <div class="container my-5">
    	<br>
    	<br>
    	<br>
					<span class="my-5 h5 text-black-50" > Filtra per Tag:</span>
						<form action="ViewAdList" method="get" id="searchForm">
							<p class="text-primary">
								<input type="text" name="materia" placeholder="Materia" class="border rounded">
								<input type="text" name="universita" placeholder="Università " class="border rounded">
								<input type="text" name="corsoDiLaurea" placeholder="Corso di Laurea" class="border rounded">
								<input type="submit" class="btn bg-warning btn-md text-white font-weight-bold" value="Filtra"/>
							</p>
						</form>
    	<c:if test="${ads == null}">
    		<p class="my-5 mx-5 h5"> NESSUNA INSERZIONE DA VISUALIZZARE </p>
    	</c:if>
    	
    	<c:if test="${ads != null}">
			<div align="center" class=" my-5">
				<div align="left" class="my-5">
					
					<!--  <span class="h5 my-5" > Tipo di Inserzione da ricercare:</span>
					
					<p>
						<form action="ViewAdList" method="post">
							<c:choose>
							    <c:when test="${selectedType == 'all'}">
							       <span> <input type="radio" name="tipo" value="all" checked/>All</span>
									<span> <input type="radio" name="tipo" value="pdf" /> PDF </span>
									<span> <input type="radio" name="tipo" value="video" /> Video</span>
							    </c:when>
							    <c:when test="${selectedType == 'pdf'}">
							        <span> <input type="radio" name="tipo" value="all" />All</span>
									<span> <input type="radio" name="tipo" value="pdf" checked/> PDF </span>
									<span> <input type="radio" name="tipo" value="video" /> Video</span>
							    </c:when>
							    <c:otherwise>
							        <span> <input type="radio" name="tipo" value="all" />All</span>
									<span> <input type="radio" name="tipo" value="pdf" /> PDF </span>
									<span> <input type="radio" name="tipo" value="video" checked/> Video</span>
							    </c:otherwise>
							</c:choose>
							<span> <input type="submit" class="btn bg-success btn-md text-white font-weight-bold ml-5" value="APPLICA"/> </span>
						</form>
					</p> -->
				</div>
		        <!--First row-->
	       
	        	<c:set var = "cont" scope = "request" value ="${0}" />
					<c:forEach items="${ads}" var="ad">
						<c:if test="${ cont % 3 == 0}">
	        				<div class="row features-small mt-5 wow fadeIn">
	        			</c:if>
	
			          <!--Grid column-->
			          		<div class="col-xl-4 col-lg-6">
			            <!--Grid row-->
			            		<div class="row">
			           
			               			<div class="col-10 mb-2 pl-3 mx-4">
			               				<form action="GetAdInfo" method="get">
											<textarea name="adIdByList" hidden>${ad.getId()}</textarea>
							  				<span><input type="image" src="${ad.getPreview().getImage()}" height="200" width="200" /></span>
							  			</form>
			                  			<h5 class="feature-title font-bold mb-1 text-truncate">${ad.getTitle()}</h5>
										<p class="grey-text mt-2"><span>${ad.getSubject()}</span></p>
										<p class="text-danger mt-2 text-bold"><span>${ad.getPrice()} <i class="fas fa-euro-sign"></i></span></p>
										
									</div>
								</div>
									<!--/Grid row-->
							</div>
			          <!--/Grid column-->
			              
	        	<c:set var = "cont" scope = "request" value ="${cont+1}" />
					<c:if test="${ cont % 3 == 0}">
						</div>
						<!--/First row-->
					</c:if>
	    	</c:forEach>
	     </c:if>
     
      <!--Section: More-->			
	 	</div>
	</div>
	<br>
	<br>
	<footer class="page-footer position-fixed col-12 footer-card">
  			<p class="footerDisclaimer text-center"><span>ReNotes 2019-2020</span></p>
		</footer>
  </main>
</body>
</html>
