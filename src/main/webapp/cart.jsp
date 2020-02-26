<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Carrello</title>
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

<body>
<!-- Header content -->
<jsp:include page="navbar.jsp"></jsp:include>
	<main>
		  <br> <br>
    <div class="container">
    	<c:if test="${ads == null && bundles == null}">
    		<img src="webImg/emptyCart.png"  class="my-5 mx-5">
    	</c:if>
    	
    	<c:if test="${ads != null || bundles != null}">
			<div align="center" class=" my-5">
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
										
										<span><a href="GetAdInfo?adIdByCart=${ad.getId()}&viewButton=false"><img src="${ad.getPreview().getImage()}" height="200" width="200"></img></a></span>
			                  			<h5 class="feature-title font-bold mb-1 text-truncate">${ad.getTitle()}</h5>
										<p class="grey-text mt-2"><span>${ad.getSubject()}</span></p>
										<form method="get" action="ManageCart">
		              						<textarea name="adIdRemove" hidden>${ad.getId()}</textarea>
											<span><input type="submit" class="btn bg-danger btn-md text-white font-weight-bold" value="Rimuovi"/></span>
										</form>
									</div>
								</div>
									<!--/Grid row-->
							</div>
			          <!--/Grid column-->
			              
	        	<c:set var = "cont" scope = "request" value ="${cont+1}" />
					<c:if test="${ cont % 3 == 0}">
						</div>
					</c:if>
	    	</c:forEach>
	    	
	    	<c:set var = "cont7" scope = "request" value ="${cont}" />
	    		 <c:forEach items="${bundles}" var="bundle">
	    		 	<c:if test="${ cont7 % 3 == 0}">
        				<div class="row features-small mt-5 wow fadeIn">
        			</c:if>
        			
          			<div class="col-xl-4 col-lg-6">
	          		  <!--Grid row-->
	            		<div class="row">
	           
	               			<div class="col-10 mb-2 pl-3 mx-4">
	          
					  			<span><a href="GetBundleInfo?bundleId=${bundle.getBundle_id()}&viewButton=false"><img src="${bundle.getImage()}" height="200" width="200" alt="img" ></img></a></span>
				
	                  			<h5 class="feature-title font-bold mb-1 text-truncate">${bundle.getTitle()}</h5>
	                  			<p><br></p>
								<span><a href="ManageCart?bundleIdRemove=${bundle.getBundle_id() }"><input type="submit" class="btn bg-danger btn-md text-white font-weight-bold" value="Rimuovi"/></a></span>
								
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
				
	     </c:if>
      <!--Section: More-->
      		
	 		<c:if test="${ (cont7) % 3 != 0}">
				</div>
			</c:if>
	 		<!--<c:if test="${ cont7 % 3 != 0}">
				</div>
				
			</c:if>-->
	 		<c:if test="${ads != null || bundles != null}">
	 			<font color="b00000">		
					<p class="h4"><span>Totale: ${cart.getTotal()} <a class="fas fa-euro-sign"></a></span></p>
				</font>
				<section>
					<form method="post" action="ViewCheckout">
						<input type="submit" class="btn bg-success btn-md text-white font-weight-bold" value="Acquista"/>
					</form>	
					<form method="get" action="ManageCart">
						<textarea name="clearCart" hidden>${ads.size() + bundles.size()}</textarea>
						<input type="submit" class="btn bg-warning btn-md text-white font-weight-bold" value="Svuota Carrello"/>
					</form>
				</section>
			 </c:if>
	</div>
	<br>
	<br>
  </main>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
