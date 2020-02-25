<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Profilo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    
    <meta name="google-signin-client_id" content="263763617027-i5vlk7ff7c7snr3r4iig9q31be5acm34.apps.googleusercontent.com">
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  </head>
  
  <body>
    <div class="container bootstrap snippet">
      <div class="row">
        <div class="col-sm-10"><h1><strong>Il mio profilo</strong></h1></div>
        <form action="ViewAdList" method="post">
        	<input type="image" src= "bootstrap2/resources/icon1_black.png" width="60" height="60" class="pull-right">
        </form>
      </div>
      <div class="row">
        <div class="col-sm-3"><!--left col-->
                
  
        <div class="text-center">
          <form action="ModifyUserImage" method="POST" enctype="multipart/form-data">  
          <img src="webImg/avatar.png" class="avatar img-circle img-thumbnail" alt="avatar">
          <h6>Scegli la foto di profilo che preferisci</h6>
          <input type="file" name="file" class="text-center center-block file-upload">
          <input type="submit" value="Applica">
          </form>
       	</div></hr><br>
  
            <hr>
            <div class="panel panel-default">	
              <div class="panel-heading"><a href="addMyPurchased"><strong>I miei acquisti</strong></a><i class="fa fa-link fa-1x"></i></div>
              <div class="panel-heading"><a href="addMyAdvertisement"><strong>Le mie inserzioni</strong></a><i class="fa fa-link fa-1x"></i></div>
			  <div class="panel-heading"><a href=""><strong>I miei bundle</strong></a><i class="fa fa-link fa-1x"></i></div>
              <div class="panel-heading"><a href="addPayment.html"><strong>Carica metodo di pagamento</strong></a><i class="fa fa-link fa-1x"></i></div>
              <div class="panel-heading"><a href="advertisement_form.jsp"><font color="00A000"><strong>Carica inserzione</strong></font></a><i class="fa fa-link fa-1x"></i></div>
			  <div class="panel-heading"><a href=""><font color="00A000"><strong>Crea Bundle</strong></font><i class="fa fa-link fa-1x"></i></div></form> 
            </div>
            <button class="btn btn-danger" onclick="logOut()">Disconnettiti</button>
          </div><!--/col-3-->
        
          <div class="col-sm-0">  
            <div class="tab-content">
              <div class="tab-pane active" id="home">
                  <hr>
                      <form class="form" action="ModifyUserServlet" method="POST" id="registrationForm">
                        <div class="form-group">
                            
                            <div class="col-xs-6">
                                <label for="email"><h4>Username</h4></label>
                                <input type="text" class="form-control" name="" id="username-input" placeholder="username" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            
                            <div class="col-xs-6">
                              <label for="email"><h4>Email</h4></label>
                                <input type="text" class="form-control" name="" id="email-input" placeholder="email" readonly>
                            </div>
                        </div>
            
					   <div class="form-group">
                            
                            <div class="col-xs-6">
                                <label for="password"><h4>Password</h4></label>
                                <input type="password" class="form-control" name="password" id="password" placeholder="password">
                            </div>
                        </div>
                        
                        <input readonly placeholder="" style="border:none; width:30px;" id="rate"><i class="fas fa-star text-primary"></i>
                        
                        
                        <div class="form-group">
                             <div class="col-xs-12">
                                  <br>
                                  <button class="btn btn-lg btn-success pull-right" type="submit"><i class="glyphicon glyphicon-ok-sign"></i> Applica modifiche</button>
                             </div>
                        </div>
                    </form>
                
                <hr>
                
               </div><!--/tab-pane-->
               
            </div><!--/tab-content-->
  
          </div><!--/col-9-->
      </div><!--/row-->
  </body>

  <script type="text/JavaScript" src="bootstrap2/js/profile.js"></script>
	
	

</html>