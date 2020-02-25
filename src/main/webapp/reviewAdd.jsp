<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Aggiungi Vautazione</title>
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

	<main>
	<br>
	<form method="post">
    <div class="container">
	<div class="row">
        <div class="col-sm-10"><h1><strong></strong></h1></div>
        <img src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right"></img>
      </div>
      <!--Section: Main info-->
      <section class="mt-5 wow fadeIn">
        <!--Grid row-->
        <div class="row">
          <!--Grid column-->
		  <div class="col-1 mb-3"></div>
          <div class="col-md-5 mb-3">

           <p class="font-weight-bold h1"> Valuta l'inserzione: <span>${adTitle}</span></p>
		   <p class="h4"> Qualità: 
			   <span><select class="form-control my-2" name="qualityValue">
					<option>5</option>
					<option>4</option>
					<option>3</option>
					<option>2</option>
					<option>1</option>
				</select></span></p>
			<p class="h4"> Affidabilità: 
			   <span><select class="form-control my-2" name="reliabilityValue">
					<option>5</option>
					<option>4</option>
					<option>3</option>
					<option>2</option>
					<option>1</option>
				</select></span></p>
			  <p class="h4"> Completezza: 
			   <span><select class="form-control my-2" name="completenessValue">
					<option>5</option>
					<option>4</option>
					<option>3</option>
					<option>2</option>
					<option>1</option>
				</select></span></p>
			  <hr class="my-3 hr-bold">
			  <h3 class="h3 my-1">Commento</h3>
			  
			  <textarea rows="4" cols="60" class="rounded" name="comment"></textarea>
			  
			  <input class="btn btn-success btn-md" type="submit"/>
			  <input class="btn btn-danger btn-md" type="reset"/>
          </div>
		</div>
	</section>
</div>
	</form>
  </main>
	<br>
	<br>
	<br>
	<footer class="page-footer position-fixed col-12 footer-card">
  			<p class="footerDisclaimer text-center"><span>ReNotes 2019-2020</span></p>
	</footer>
</body>
</html>
