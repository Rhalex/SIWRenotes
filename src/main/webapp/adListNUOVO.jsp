<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>AdList</title>
  <!-- BULMA -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.1/css/bulma.min.css">
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
  <!--  <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>-->
 
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.js"></script>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.12.4.js"></script>
  <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 
  <script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-autocomplete/1.0.7/jquery.auto-complete.js"
	integrity="sha256-K3qK8ynOxhJVloLac0CTWwr7iFKVDZF4Gd2yEsiAZYA="
	crossorigin="anonymous"></script>
 
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="js/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="js/mdb.min.js"></script>
  
   <script type="text/javascript" src="js/javaScriptUtility.js"></script>
   <script type="text/javascript" src="js/autocomplete.js"></script>
  <!-- Initializations -->
</head>

<body >
<!-- Header content -->
	<header>
<!-- NavBar -->
  <nav class="navbar fixed-top navbar-expand-lg navbar-dark scrolling-navbar">
    <div class="container">

      <!-- Brand -->
      <form action="ViewAdList" method="get" class="mx-5">
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
     			<div class="input-group dropdown is-active dropdown-trigger">
				        <div class="md-form mt-2 mb-2">
      				 		<input type="text" class="input select text-white" placeholder="Inserisci il Titolo" aria-label="Search" aria-describedby="basic-addon2" name="titolo" id="aTitolo" oninput="auto('aTitolo','dropdown-menu','suggestion','titolo')"autocomplete=off form="searchForm" />
							<div  id="dropdown-menu" role="menu"></div>
							
					</div>
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
		<br><br><br>
		<span class="h5 text-black-50" > Filtra per Tag:</span>
			
				<form action="ViewAdList" method="get" id="searchForm">
					<p class="text-primary">
						<div class="row">
						<div class="input-group col-3">
							 <div class="md-form mt-2 mb-2">
      		 					<input type="text" class="form-control" placeholder="Materia" aria-label="Search" aria-describedby="basic-addon2" name="materia" id="m" oninput="auto('m','Mmenu','suggestion2')" autocomplete=off >
								<div  id="Mmenu" role="menu"></div>
							</div>
						</div>
						<div class="input-group col-3">
							 <div class="md-form mt-2 mb-2">
      		 					<input type="text" class="form-control" placeholder="Università " aria-label="Search" aria-describedby="basic-addon2" name="universita" id="u" oninput="auto('u','Umenu','suggestion3')" autocomplete=off >
								<div  id="Umenu" role="menu"></div>
							</div>
						</div>
						<div class="input-group col-3">
							 <div class="md-form mt-2 mb-2">
      		 					<input type="text" class="form-control" placeholder="Corso Di Laurea" aria-label="Search" aria-describedby="basic-addon2" name="corsoDiLaurea" id="c" oninput="auto('c','Cmenu','suggestion4')" autocomplete=off >
								<div  id="Cmenu" role="menu"></div>
							</div>
						</div>
							<div class="col-3">
							<input type="submit" class="btn bg-success btn-md text-white font-weight-bold" value="Ricerca" hidden/>
							</div>
						</div>
							
					</p>
				</form>
		<c:if test="${ads == null && searchedBundles == null}">
			<!-- Se non ci sono Match nelle inserzioni-->
			<c:if test="${search == true}">
    			<p class="my-5 mx-5 h5"> NESSUNA INSERZIONE CORRISPONDENTE ALLA TUA RICERCA </p>
    		</c:if>
			<!-- Se non c'è stata ricerca-->
			<c:if test="${search == false}">
				<c:if test="${adsMR != null}">
					<span class="h5 mx-5">Le inserzioni più recensite:</span>
					<div class=" my-5" >
				        <!--First row-->
			        	<div class="row features-small mt-5 wow fadeIn" align="center">
				          <!--Grid column-->
			          		<c:set var = "cont" scope = "request" value ="${0}" />
							<c:forEach items="${adsMR}" var="ad">
								<c:if test="${ cont % 3 == 0}">
									</div>
			        				<div class="row features-small mt-5 wow fadeIn" align="center">
			        			</c:if>
			
					          <!--Grid column-->
					          		<div class="col-xl-4 col-lg-6">
					            <!--Grid row-->
					            		<div class="row">
					           
					               			<div class="col-10 mb-2 pl-3 mx-4">
					               				<span><a href="GetAdInfo?adIdByList=${ad.getId()}"><img type="image" src="${ad.getPreview().getImage()}" height="200" width="200" ></img></a></span>
					                  			<h5 class="feature-title font-bold mb-1 text-truncate">${ad.getTitle()}</h5>
												<p class="grey-text mt-2"><span>${ad.getSubject()}</span></p>
												<p class="text-danger mt-2 text-bold"><span>${ad.getPrice()} <i class="fas fa-euro-sign"></i></span></p>
												<p class="grey-text mt-2"><span>${ad.getValue()}</span> <a class="fas fa-star text-warning"></a></p>
											</div>
										</div>
											<!--/Grid row-->
									</div>
						          <!--/Grid column-->
						              
					        		<c:set var = "cont" scope = "request" value ="${cont+1}" />
									<c:if test="${ cont % 3 == 0}">
										
										<!--/First row-->
									</c:if>
					    		</c:forEach>
							</div>
						</c:if>
						<c:if test="${valueAds != null}">
							<span class="h5 mx-5">Le inserzioni migliori:</span>
							<div class=" my-5" >
							<div class="row features-small mt-5 wow fadeIn" align="center">
						          <!--Grid column-->
						          <c:set var = "cont2" scope = "request" value ="${0}" />
								  <c:forEach items="${valueAds}" var="ad">
									<c:if test="${ cont2 % 3 == 0}">
										</div>
				        				<div class="row features-small mt-5 wow fadeIn" align="center">
				        			</c:if>
				        			
						          		<div class="col-xl-4 col-lg-6">
						            <!--Grid row-->
						            		<div class="row">
						           
						               			<div class="col-10 mb-2 pl-3 mx-4">	
										  			<span><a href="GetAdInfo?adIdByList=${ad.getId()}"><img type="image" src="${ad.getPreview().getImage()}" height="200" width="200" ></img></a></span>
						                  			<h5 class="feature-title font-bold mb-1 text-truncate">${ad.getTitle()}</h5>
													<p class="grey-text mt-2"><span>${ad.getSubject()}</span></p>
													<p class="text-danger mt-2 text-bold"><span>${ad.getPrice()}<i class="fas fa-euro-sign"></i></span></p>
													<p class="grey-text mt-2"><span>${ad.getValue()}</span> <a class="fas fa-star text-warning"></a></p>
													
												</div>
											</div>
												<!--/Grid row-->
										</div>
										
										<c:set var = "cont2" scope = "request" value ="${cont2+1}" />
										<c:if test="${ cont2 % 3 == 0}">
											</div>
										<!--/First row-->
										</c:if>
									</c:forEach>	
							</div>
						</c:if>

						<c:if test="${bundles != null }">
							<span class="h5 mx-5" >Bundle:</span>
							<div class="row features-small mt-5 wow fadeIn" align="center">
						          <!--Grid column-->
						          	 <c:set var = "cont3" scope = "request" value ="${0}" />
									  <c:forEach items="${bundles}" var="bundle">
						          		<div class="col-xl-4 col-lg-6">
						            <!--Grid row-->
						            		<div class="row">
						           
						               			<div class="col-10 mb-2 pl-3 mx-4">
						          
										  			<span><a href="GetBundleInfo?bundleId=${bundle.getBundle_id()}"><img src="${bundle.getImage()}" height="200" width="200" alt="img" ></img></a></span>
									
						                  			<h5 class="feature-title font-bold mb-1 text-truncate">${bundle.getTitle()}</h5>
													<p class="text-danger mt-2 text-bold"><span>${bundle.getPrice()}<i class="fas fa-euro-sign"></i></span></p>
													
												</div>
											</div>
												<!--/Grid row-->
										</div>
									</c:forEach>
							</div>
					</c:if>
					
			</c:if>
    	</c:if>
    	
    	<c:if test="${(ads != null) || searchedBundles != null}">
			<div align="center" class=" my-5">
				<div align="left" class="my-5">
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
			               				<span><a href="GetAdInfo?adIdByList=${ad.getId()}"><img type="image" src="${ad.getPreview().getImage()}" height="200" width="200" ></img></a></span>
			                  			<h5 class="feature-title font-bold mb-1 text-truncate">${ad.getTitle()}</h5>
										<p class="grey-text mt-2"><span>${ad.getSubject()}</span></p>
										<p class="text-danger mt-2 text-bold"><span>${ad.getPrice()} <i class="fas fa-euro-sign"></i></span></p>
										<p class="grey-text mt-2"><span>${ad.getValue()}</span> <a class="fas fa-star text-warning"></a></p>
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
	    		
	    		<c:set var = "cont7" scope = "request" value ="${0}" />
	    		 <c:forEach items="${searchedBundles}" var="bundle">
	    		 	<c:if test="${ cont7 % 3 == 0}">
        				<div class="row features-small mt-5 wow fadeIn">
        			</c:if>
        			
          			<div class="col-xl-4 col-lg-6">
	          		  <!--Grid row-->
	            		<div class="row">
	           
	               			<div class="col-10 mb-2 pl-3 mx-4">
	          
					  			<span><a href="GetBundleInfo?bundleId=${bundle.getBundle_id()}"><img src="${bundle.getImage()}" height="200" width="200" alt="img" ></img></a></span>
				
	                  			<h5 class="feature-title font-bold mb-1 text-truncate">${bundle.getTitle()}</h5>
								<p class="text-danger mt-2 text-bold"><span>${bundle.getPrice()}<i class="fas fa-euro-sign"></i></span></p>
								
							</div>
						</div>
							<!--/Grid row-->
					</div>
					<c:set var = "cont7" scope = "request" value ="${cont7+1}" />
					<c:if test="${ cont7 % 3 == 0}">
						</div>
						<!--/First row-->
					</c:if>
				</c:forEach>
    		  </div>
    		</div>
	     </c:if>
      <!--Section: More-->			
	 	
	</div>
	<br>
	<br>
	  </main>
	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
