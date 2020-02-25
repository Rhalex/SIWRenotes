<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Summary Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	
	<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
	<script type="text/javascript" src="fusionChartsSuitXT/js/fusioncharts.js"></script>
	<script type="text/javascript" src="fusionChartsSuitXT/js/themes/fusioncharts.theme.fusion.js"></script>

	<script type="text/javascript" src="js/diagrams.js"></script>
   
  </head>
  
  <body onload="setSummaryDia('column3d')">
    <div class="container bootstrap snippet">
      <div class="row">
        <div class="col-sm-10"><h1><strong>Dettagli Profilo</strong></h1></div>
        <form action="ViewAdList" method="post">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
      </div>
      <div class="row">
        <div class="col-sm-3"><!--left col-->
                
  
        <div class="text-center">
          <form action="ModifyUserImage" method="POST" enctype="multipart/form-data">  
          <img src="webImg/avatar.png" class="avatar img-circle img-thumbnail" alt="avatar">
          
          </form>
       	</div></hr><br>
          </div><!--/col-3-->
        
          <div class="col-sm-0">  
            <div class="tab-content">
              <div class="tab-pane active" id="home">
                  <hr>
                      <form class="form" action="ModifyUserServlet" method="POST" id="registrationForm">
                        <div class="form-group">
                            
                            <div class="col-xs-6">
                                <p><label class="h4">Username:</label></p>
                                <p><label >${user.getUsername()}</label></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-6">
                              <label for="email"><h4>Email:</h4>${user.getMail()}</label>
                                <label id="email-input" placeholder="email" readonly></label>
                            </div>
                        </div>
							 <input readonly placeholder="${user.getRate()}" style="border:none; width:30px;" id="rate"><i href="" class="fas fa-star text-primary"></i>
                   	 </form>
                
                <hr>
		</div><!--/tab-content-->
  	</div>
 </div><!--/col-9-->
</div> 

	<div class="row fadeIn">
			<div class="col-3"></div>
			<div class="col-5">
				<div id="chart-container2"></div>
				<button class="btn" onclick="setSummaryDia('column2d')">COLUMN 2D</button>
				<button class="btn" onclick="setSummaryDia('column3d')">COLUMN 3D</button>
			</div>
			<div class="col-3"></div>
		</div>
  </body>

</html>
