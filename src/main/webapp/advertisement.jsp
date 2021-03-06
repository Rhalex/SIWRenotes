<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<head>
  <meta charset="utf-8">
  <title>Le mie Inserzioni</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="bootstrap2/css/advertisement.css">
</head>


<body>
<div class="container bootstrap snippet">
    <div class="row">
      <div class="col-sm-10"><h1><strong>Le mie inserzioni</strong></h1></div>
      <form action="ViewAdList" method="post">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
    </div>
	
	<c:if test="${ads == null}">
    		<p class="my-5 mx-5 h5"> NESSUNA INSERZIONE DA VISUALIZZARE </p>
    </c:if>
    	
    <c:if test="${ads != null}">
    <div align="center" class="my-5">
    <hr>
    <c:forEach items="${ads}" var="ad">
		
		    <div class="row">
				<div id="rounded-div">
					<div class="col-sm-3" align="justify">
						<a href="GetAdInfo?adIdByList=${ad.getId()}&viewButton=false"><img type="image" src="${ad.getPreview().getImage()}"  name="adImage" class="img-square img-thumbnail" alt="avatar"></a>
					</div>
					<div class="col-sm-0 mx-5 text-truncate" align="left">
						<p><font color="white"><h2 class="text-truncate text-white" name="adTitle" ><strong>${ad.getTitle()}</strong></h2></font></p>
						<p><font color="white"><h4 name="adSubject">${ad.getSubject()}</h4></font></p>
						<p><font color="white"><h4 name="adDegreeCourse">${ad.getDegreeCourse() }</h4></font></p>
						<p><font color="white"><h4 name="adUniversity">${ad.getUniversity() } </h4></font></p>
					</div>
					
					<div class="row col-sm-9">
					<div class="col-sm-8"></div>
						<div class="col-sm-2" id="botton-padding" align="right">
							<form action="ViewModifyAd" method="post">
								<textarea name="adIdModify" hidden>${ad.getId()}</textarea>
								<button type="submit" class="btn btn-lg btn-warning">Modifica</button>
							</form>
						</div>
						<div class="col-sm-2" id="botton-padding" align="right">
							<a href="ModifyAdvertisement?adIdRemove=${ad.getId()}"><button type="submit"  class="btn btn-lg btn-danger">Rimuovi</button></a>
						</div>
					</div>
				</div>
		    </div><!--/row-->
		<hr>
	</c:forEach>
	</div>
	</c:if>
</div>
</body>
                                                      