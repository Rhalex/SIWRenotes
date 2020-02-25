<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
  <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
  <link rel="stylesheet" href="bootstrap2/purchased.css">
</head>


<body>
<div class="container bootstrap snippet">
    <div class="row">
      <div class="col-sm-10"><h1><strong>I miei acquisti</strong></h1></div>
     <form action="ViewAdList" method="post">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
    </div>
	
	 <div class="container my-5">
    	
    	<c:if test="${ads == null}">
    		<p class="my-5 mx-5 h5"> NESSUNA INSERZIONE DA VISUALIZZARE </p>
    	</c:if>
    	
    	<c:if test="${ads != null}">
			<div align="center" class=" my-5">
				<!--First row-->
	       
					<c:forEach items="${ads}" var="ad">
						<hr>
		   				 <div class="row">
							<div id="rounded-div">
								<div class="col-sm-3">
								<a href="GetAdInfo?adIdByList=${ad.getId()}&viewButton=false"><input type="image" src="${ad.getPreview().getImage()}" class="img-square img-thumbnail" alt="avatar"></a>
								</div>
							
								<div class="col-sm-2">
									<p><font color="white"><h1 class="text-truncate text-white"><strong>${ad.getTitle()}</strong></h1></font></p>
									<p><font color="white"><h4>${ad.getSubject()}</h4></font></p>
									<p><font color="white"><h4>${ad.getDegreeCourse() }</h4></font></p>
									<p><font color="white"><h4>${ad.getUniversity() } </h4></font></p>
								</div>
								<div class="col-sm-1" id="div-button">
									<a class="btn btn-lg btn-success" href="${ad.getFile()}">Apri</a>
								</div>
		   				 </div><!--/row-->
		   				 </div>
						<hr>
	    			</c:forEach>
	    			</div>
	     </c:if>
		</div>
</div>
</body>
                                                      