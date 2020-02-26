<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<head>
  <meta charset="utf-8">
  <title>Crea Bundle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

  <link rel="stylesheet" href="bootstrap2/css/advertisement.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
  <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
   <script type="text/javascript" src="js/javaScriptUtility.js"></script>
</head>

<body>
<div class="container bootstrap snippet">
    <div class="row">
      <div class="col-sm-10"><h1><strong>Crea Bundle</strong></h1></div>
      <form action="ViewAdList" method="get">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
    </div>
	
	<c:if test="${ads == null}">
    	<p class="my-5 mx-5 h5"> Non hai inserzioni pubblicate per poter creare un Bundle </p>
    </c:if>
    	
    <c:if test="${ads != null}">
		<!-- Prova -->
	<form name="create" id="form">
	    <div align="center" class="my-5">
	    
	    <div align="left">
				<label>
					<h4>Titolo del Bundle</h4>
					<input type="text" class="form-control" placeholder="Titolo" name="titolo" id="titoloBundle" oninput="checkBundleTitle()" autocomplete=off required>
					
				</label>
				<p id="resp" class="text-danger"></p>
			</div>
		<label class="h4">Scegli da 2 a 5 inserzioni da pubblicare in un bundle:</label>
		 <div align="center" class="my-5">
			<hr>
			    <div class="row">
			    	<c:set var = "adId" scope = "request" value ="${0}" />
				    <c:forEach items="${ads}" var="ad">
						<div id="rounded-div">
							<div class="col-sm-3">
								 <img type="image" src="${ad.getPreview().getImage()}" class="img-square img-thumbnail" alt="avatar">
							</div>
							<div class="col-sm-0 mx-5 text-truncate" align="left" >
								<p><font color="white"><h2 class="text-truncate text-white text-" name="adTitle" >${ad.getTitle()}</h2></font></p>
								<p><font color="white"><h4>${ad.getSubject()}</h4></font></p>
								<p><font color="white"><h4>${ad.getDegreeCourse()}</h4></font></p>
								<p><font color="white"><h4>${ad.getUniversity()}</h4></font></p>
								<p><font color="#EB1B1F"><h4 name="adPrezzo" id="prezzoAd">${ad.getPrice()} <i class="fas fa-euro-sign"></i></h4></font></p>
							</div>
							<div class="col-sm-0 my-5" align="right" >
									<c:set var = "adId" scope = "request" value ="${adId + 1}" />
									
									<label><h4>Aggiungi al bundle <input type="checkbox" name="${adId}" id="${adId}" value="${ad.getId()}"></h4></label>
								<div></div>
							</div>
						</div>
						<hr>
					</c:forEach>
					<textarea name="adIdMax" hidden >${adId}</textarea>
			    </div><!--/row-->
				 
		</div>
		<div align="right">
			<label for="Prezzo"><h4>Prezzo del Bundle:</h4>
	            <input type="number" min="0" step="0.01" class="form-control" name="prezzo" id="prezzoBunlde" placeholder="€" required><br>
			</label>
		</div>
		</form>	
		<div align="right">
			<button class="btn btn-success" id="bottone" onclick="checkBundleItems('${adId}')">Crea Bundle</button>
		</div>
			<br><br>
			<!-- /Prova-->
		</div>
	
	</c:if>
</div>
</body>
</html>