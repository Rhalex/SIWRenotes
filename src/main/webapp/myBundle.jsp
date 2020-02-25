<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
  <meta charset="utf-8">
  <title>I miei Bundle</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="bootstrap2/css/advertisement.css">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
</head>


<body>
<div class="container bootstrap snippet">
    <div class="row">
      <div class="col-sm-10"><h1><strong>I miei bundle</strong></h1></div>
      <form action="ViewAdList" method="get">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
    </div>
	
	<c:if test="${bundles != null}">		
		 <div align="center" class="my-5">
			<hr>
			<div class="row">
				<c:forEach items="${bundles}" var="bundle"> 
					<div id="rounded-div">
						<div class="col-sm-3" align="left">
							<a href="GetBundleInfo?bundleId=${bundle.getBundle_id()}&viewButton=false"><img type="image" src="${bundle.getImage()}"  name="adImage" class="img-square img-thumbnail" alt=""></a>
						</div>
						<div class="col-sm-4 mx-2" align="left" >
							<p><font color="white"><h4 class="text-truncate text-white" name="bundleTitle" ><strong>${bundle.getTitle()}</strong></h4></font></p>
							<c:forEach items="${bundle.getAds()}" var="ad">
								<p><a href="GetAdInfo?adIdByList=${ad.getId()}&viewButton=false" ><font color="white"><h5 name="adTitle1">${ad.getTitle()}</h5></font></a></p>
							</c:forEach>
							<p><font color="#EB1B1F"><h5 name="adPrezzo">${bundle.getPrice()} <i class="fas fa-euro-sign"></i></h5></font></p>
						</div>
						
						<div class="col-sm-3" id=" botton-padding" >
							<label class="text-center">
								<br>
								<br>
								<br>
								<form action="RemoveMyBundle" method="get">
									<button type="submit"  class="btn btn-lg btn-danger my-5" name="bundleId" value="${bundle.getBundle_id()}">Rimuovi</button>
								</form>
							</label>
						</div>
					</div>
						<hr>
					</c:forEach>
		    </div><!--/row-->
			<hr>	 
		</div>
    </c:if>
    	
    <c:if test="${bundles == null}">
    	<p class="my-5 mx-5 h5"> NESSUN BUNDLE DA VISUALIZZARE </p>
	</c:if>
</div>
</body>
</html>